package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.repository.DosyaBorcRepository;
import com.er.fin.service.DosyaBorcService;
import com.er.fin.repository.search.DosyaBorcSearchRepository;
import com.er.fin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.er.fin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DosyaBorcResource REST controller.
 *
 * @see DosyaBorcResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DosyaBorcResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private DosyaBorcRepository dosyaBorcRepository;

    @Autowired
    private DosyaBorcService dosyaBorcService;

    @Autowired
    private DosyaBorcSearchRepository dosyaBorcSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDosyaBorcMockMvc;

    private DosyaBorc dosyaBorc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DosyaBorcResource dosyaBorcResource = new DosyaBorcResource(dosyaBorcService);
        this.restDosyaBorcMockMvc = MockMvcBuilders.standaloneSetup(dosyaBorcResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DosyaBorc createEntity(EntityManager em) {
        DosyaBorc dosyaBorc = new DosyaBorc()
            .kod(DEFAULT_KOD);
        return dosyaBorc;
    }

    @Before
    public void initTest() {
        dosyaBorcSearchRepository.deleteAll();
        dosyaBorc = createEntity(em);
    }

    @Test
    @Transactional
    public void createDosyaBorc() throws Exception {
        int databaseSizeBeforeCreate = dosyaBorcRepository.findAll().size();

        // Create the DosyaBorc
        restDosyaBorcMockMvc.perform(post("/api/dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaBorc)))
            .andExpect(status().isCreated());

        // Validate the DosyaBorc in the database
        List<DosyaBorc> dosyaBorcList = dosyaBorcRepository.findAll();
        assertThat(dosyaBorcList).hasSize(databaseSizeBeforeCreate + 1);
        DosyaBorc testDosyaBorc = dosyaBorcList.get(dosyaBorcList.size() - 1);
        assertThat(testDosyaBorc.getKod()).isEqualTo(DEFAULT_KOD);

        // Validate the DosyaBorc in Elasticsearch
        DosyaBorc dosyaBorcEs = dosyaBorcSearchRepository.findOne(testDosyaBorc.getId());
        assertThat(dosyaBorcEs).isEqualToIgnoringGivenFields(testDosyaBorc);
    }

    @Test
    @Transactional
    public void createDosyaBorcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dosyaBorcRepository.findAll().size();

        // Create the DosyaBorc with an existing ID
        dosyaBorc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosyaBorcMockMvc.perform(post("/api/dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaBorc)))
            .andExpect(status().isBadRequest());

        // Validate the DosyaBorc in the database
        List<DosyaBorc> dosyaBorcList = dosyaBorcRepository.findAll();
        assertThat(dosyaBorcList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDosyaBorcs() throws Exception {
        // Initialize the database
        dosyaBorcRepository.saveAndFlush(dosyaBorc);

        // Get all the dosyaBorcList
        restDosyaBorcMockMvc.perform(get("/api/dosya-borcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosyaBorc.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getDosyaBorc() throws Exception {
        // Initialize the database
        dosyaBorcRepository.saveAndFlush(dosyaBorc);

        // Get the dosyaBorc
        restDosyaBorcMockMvc.perform(get("/api/dosya-borcs/{id}", dosyaBorc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dosyaBorc.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDosyaBorc() throws Exception {
        // Get the dosyaBorc
        restDosyaBorcMockMvc.perform(get("/api/dosya-borcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDosyaBorc() throws Exception {
        // Initialize the database
        dosyaBorcService.save(dosyaBorc);

        int databaseSizeBeforeUpdate = dosyaBorcRepository.findAll().size();

        // Update the dosyaBorc
        DosyaBorc updatedDosyaBorc = dosyaBorcRepository.findOne(dosyaBorc.getId());
        // Disconnect from session so that the updates on updatedDosyaBorc are not directly saved in db
        em.detach(updatedDosyaBorc);
        updatedDosyaBorc
            .kod(UPDATED_KOD);

        restDosyaBorcMockMvc.perform(put("/api/dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDosyaBorc)))
            .andExpect(status().isOk());

        // Validate the DosyaBorc in the database
        List<DosyaBorc> dosyaBorcList = dosyaBorcRepository.findAll();
        assertThat(dosyaBorcList).hasSize(databaseSizeBeforeUpdate);
        DosyaBorc testDosyaBorc = dosyaBorcList.get(dosyaBorcList.size() - 1);
        assertThat(testDosyaBorc.getKod()).isEqualTo(UPDATED_KOD);

        // Validate the DosyaBorc in Elasticsearch
        DosyaBorc dosyaBorcEs = dosyaBorcSearchRepository.findOne(testDosyaBorc.getId());
        assertThat(dosyaBorcEs).isEqualToIgnoringGivenFields(testDosyaBorc);
    }

    @Test
    @Transactional
    public void updateNonExistingDosyaBorc() throws Exception {
        int databaseSizeBeforeUpdate = dosyaBorcRepository.findAll().size();

        // Create the DosyaBorc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDosyaBorcMockMvc.perform(put("/api/dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaBorc)))
            .andExpect(status().isCreated());

        // Validate the DosyaBorc in the database
        List<DosyaBorc> dosyaBorcList = dosyaBorcRepository.findAll();
        assertThat(dosyaBorcList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDosyaBorc() throws Exception {
        // Initialize the database
        dosyaBorcService.save(dosyaBorc);

        int databaseSizeBeforeDelete = dosyaBorcRepository.findAll().size();

        // Get the dosyaBorc
        restDosyaBorcMockMvc.perform(delete("/api/dosya-borcs/{id}", dosyaBorc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dosyaBorcExistsInEs = dosyaBorcSearchRepository.exists(dosyaBorc.getId());
        assertThat(dosyaBorcExistsInEs).isFalse();

        // Validate the database is empty
        List<DosyaBorc> dosyaBorcList = dosyaBorcRepository.findAll();
        assertThat(dosyaBorcList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDosyaBorc() throws Exception {
        // Initialize the database
        dosyaBorcService.save(dosyaBorc);

        // Search the dosyaBorc
        restDosyaBorcMockMvc.perform(get("/api/_search/dosya-borcs?query=id:" + dosyaBorc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosyaBorc.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DosyaBorc.class);
        DosyaBorc dosyaBorc1 = new DosyaBorc();
        dosyaBorc1.setId(1L);
        DosyaBorc dosyaBorc2 = new DosyaBorc();
        dosyaBorc2.setId(dosyaBorc1.getId());
        assertThat(dosyaBorc1).isEqualTo(dosyaBorc2);
        dosyaBorc2.setId(2L);
        assertThat(dosyaBorc1).isNotEqualTo(dosyaBorc2);
        dosyaBorc1.setId(null);
        assertThat(dosyaBorc1).isNotEqualTo(dosyaBorc2);
    }
}
