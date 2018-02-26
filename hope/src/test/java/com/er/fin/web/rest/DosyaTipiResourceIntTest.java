package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.DosyaTipi;
import com.er.fin.repository.DosyaTipiRepository;
import com.er.fin.service.DosyaTipiService;
import com.er.fin.repository.search.DosyaTipiSearchRepository;
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
 * Test class for the DosyaTipiResource REST controller.
 *
 * @see DosyaTipiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DosyaTipiResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private DosyaTipiRepository dosyaTipiRepository;

    @Autowired
    private DosyaTipiService dosyaTipiService;

    @Autowired
    private DosyaTipiSearchRepository dosyaTipiSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDosyaTipiMockMvc;

    private DosyaTipi dosyaTipi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DosyaTipiResource dosyaTipiResource = new DosyaTipiResource(dosyaTipiService);
        this.restDosyaTipiMockMvc = MockMvcBuilders.standaloneSetup(dosyaTipiResource)
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
    public static DosyaTipi createEntity(EntityManager em) {
        DosyaTipi dosyaTipi = new DosyaTipi()
            .kod(DEFAULT_KOD);
        return dosyaTipi;
    }

    @Before
    public void initTest() {
        dosyaTipiSearchRepository.deleteAll();
        dosyaTipi = createEntity(em);
    }

    @Test
    @Transactional
    public void createDosyaTipi() throws Exception {
        int databaseSizeBeforeCreate = dosyaTipiRepository.findAll().size();

        // Create the DosyaTipi
        restDosyaTipiMockMvc.perform(post("/api/dosya-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaTipi)))
            .andExpect(status().isCreated());

        // Validate the DosyaTipi in the database
        List<DosyaTipi> dosyaTipiList = dosyaTipiRepository.findAll();
        assertThat(dosyaTipiList).hasSize(databaseSizeBeforeCreate + 1);
        DosyaTipi testDosyaTipi = dosyaTipiList.get(dosyaTipiList.size() - 1);
        assertThat(testDosyaTipi.getKod()).isEqualTo(DEFAULT_KOD);

        // Validate the DosyaTipi in Elasticsearch
        DosyaTipi dosyaTipiEs = dosyaTipiSearchRepository.findOne(testDosyaTipi.getId());
        assertThat(dosyaTipiEs).isEqualToIgnoringGivenFields(testDosyaTipi);
    }

    @Test
    @Transactional
    public void createDosyaTipiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dosyaTipiRepository.findAll().size();

        // Create the DosyaTipi with an existing ID
        dosyaTipi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosyaTipiMockMvc.perform(post("/api/dosya-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaTipi)))
            .andExpect(status().isBadRequest());

        // Validate the DosyaTipi in the database
        List<DosyaTipi> dosyaTipiList = dosyaTipiRepository.findAll();
        assertThat(dosyaTipiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDosyaTipis() throws Exception {
        // Initialize the database
        dosyaTipiRepository.saveAndFlush(dosyaTipi);

        // Get all the dosyaTipiList
        restDosyaTipiMockMvc.perform(get("/api/dosya-tipis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosyaTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getDosyaTipi() throws Exception {
        // Initialize the database
        dosyaTipiRepository.saveAndFlush(dosyaTipi);

        // Get the dosyaTipi
        restDosyaTipiMockMvc.perform(get("/api/dosya-tipis/{id}", dosyaTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dosyaTipi.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDosyaTipi() throws Exception {
        // Get the dosyaTipi
        restDosyaTipiMockMvc.perform(get("/api/dosya-tipis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDosyaTipi() throws Exception {
        // Initialize the database
        dosyaTipiService.save(dosyaTipi);

        int databaseSizeBeforeUpdate = dosyaTipiRepository.findAll().size();

        // Update the dosyaTipi
        DosyaTipi updatedDosyaTipi = dosyaTipiRepository.findOne(dosyaTipi.getId());
        // Disconnect from session so that the updates on updatedDosyaTipi are not directly saved in db
        em.detach(updatedDosyaTipi);
        updatedDosyaTipi
            .kod(UPDATED_KOD);

        restDosyaTipiMockMvc.perform(put("/api/dosya-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDosyaTipi)))
            .andExpect(status().isOk());

        // Validate the DosyaTipi in the database
        List<DosyaTipi> dosyaTipiList = dosyaTipiRepository.findAll();
        assertThat(dosyaTipiList).hasSize(databaseSizeBeforeUpdate);
        DosyaTipi testDosyaTipi = dosyaTipiList.get(dosyaTipiList.size() - 1);
        assertThat(testDosyaTipi.getKod()).isEqualTo(UPDATED_KOD);

        // Validate the DosyaTipi in Elasticsearch
        DosyaTipi dosyaTipiEs = dosyaTipiSearchRepository.findOne(testDosyaTipi.getId());
        assertThat(dosyaTipiEs).isEqualToIgnoringGivenFields(testDosyaTipi);
    }

    @Test
    @Transactional
    public void updateNonExistingDosyaTipi() throws Exception {
        int databaseSizeBeforeUpdate = dosyaTipiRepository.findAll().size();

        // Create the DosyaTipi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDosyaTipiMockMvc.perform(put("/api/dosya-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaTipi)))
            .andExpect(status().isCreated());

        // Validate the DosyaTipi in the database
        List<DosyaTipi> dosyaTipiList = dosyaTipiRepository.findAll();
        assertThat(dosyaTipiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDosyaTipi() throws Exception {
        // Initialize the database
        dosyaTipiService.save(dosyaTipi);

        int databaseSizeBeforeDelete = dosyaTipiRepository.findAll().size();

        // Get the dosyaTipi
        restDosyaTipiMockMvc.perform(delete("/api/dosya-tipis/{id}", dosyaTipi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dosyaTipiExistsInEs = dosyaTipiSearchRepository.exists(dosyaTipi.getId());
        assertThat(dosyaTipiExistsInEs).isFalse();

        // Validate the database is empty
        List<DosyaTipi> dosyaTipiList = dosyaTipiRepository.findAll();
        assertThat(dosyaTipiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDosyaTipi() throws Exception {
        // Initialize the database
        dosyaTipiService.save(dosyaTipi);

        // Search the dosyaTipi
        restDosyaTipiMockMvc.perform(get("/api/_search/dosya-tipis?query=id:" + dosyaTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosyaTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DosyaTipi.class);
        DosyaTipi dosyaTipi1 = new DosyaTipi();
        dosyaTipi1.setId(1L);
        DosyaTipi dosyaTipi2 = new DosyaTipi();
        dosyaTipi2.setId(dosyaTipi1.getId());
        assertThat(dosyaTipi1).isEqualTo(dosyaTipi2);
        dosyaTipi2.setId(2L);
        assertThat(dosyaTipi1).isNotEqualTo(dosyaTipi2);
        dosyaTipi1.setId(null);
        assertThat(dosyaTipi1).isNotEqualTo(dosyaTipi2);
    }
}
