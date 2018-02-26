package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.DosyaBorcKalem;
import com.er.fin.repository.DosyaBorcKalemRepository;
import com.er.fin.service.DosyaBorcKalemService;
import com.er.fin.repository.search.DosyaBorcKalemSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static com.er.fin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DosyaBorcKalemResource REST controller.
 *
 * @see DosyaBorcKalemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DosyaBorcKalemResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ORJINAL_BORC_TUTARI = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORJINAL_BORC_TUTARI = new BigDecimal(2);

    @Autowired
    private DosyaBorcKalemRepository dosyaBorcKalemRepository;

    @Autowired
    private DosyaBorcKalemService dosyaBorcKalemService;

    @Autowired
    private DosyaBorcKalemSearchRepository dosyaBorcKalemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDosyaBorcKalemMockMvc;

    private DosyaBorcKalem dosyaBorcKalem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DosyaBorcKalemResource dosyaBorcKalemResource = new DosyaBorcKalemResource(dosyaBorcKalemService);
        this.restDosyaBorcKalemMockMvc = MockMvcBuilders.standaloneSetup(dosyaBorcKalemResource)
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
    public static DosyaBorcKalem createEntity(EntityManager em) {
        DosyaBorcKalem dosyaBorcKalem = new DosyaBorcKalem()
            .kod(DEFAULT_KOD)
            .orjinalBorcTutari(DEFAULT_ORJINAL_BORC_TUTARI);
        return dosyaBorcKalem;
    }

    @Before
    public void initTest() {
        dosyaBorcKalemSearchRepository.deleteAll();
        dosyaBorcKalem = createEntity(em);
    }

    @Test
    @Transactional
    public void createDosyaBorcKalem() throws Exception {
        int databaseSizeBeforeCreate = dosyaBorcKalemRepository.findAll().size();

        // Create the DosyaBorcKalem
        restDosyaBorcKalemMockMvc.perform(post("/api/dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaBorcKalem)))
            .andExpect(status().isCreated());

        // Validate the DosyaBorcKalem in the database
        List<DosyaBorcKalem> dosyaBorcKalemList = dosyaBorcKalemRepository.findAll();
        assertThat(dosyaBorcKalemList).hasSize(databaseSizeBeforeCreate + 1);
        DosyaBorcKalem testDosyaBorcKalem = dosyaBorcKalemList.get(dosyaBorcKalemList.size() - 1);
        assertThat(testDosyaBorcKalem.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testDosyaBorcKalem.getOrjinalBorcTutari()).isEqualTo(DEFAULT_ORJINAL_BORC_TUTARI);

        // Validate the DosyaBorcKalem in Elasticsearch
        DosyaBorcKalem dosyaBorcKalemEs = dosyaBorcKalemSearchRepository.findOne(testDosyaBorcKalem.getId());
        assertThat(dosyaBorcKalemEs).isEqualToIgnoringGivenFields(testDosyaBorcKalem);
    }

    @Test
    @Transactional
    public void createDosyaBorcKalemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dosyaBorcKalemRepository.findAll().size();

        // Create the DosyaBorcKalem with an existing ID
        dosyaBorcKalem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosyaBorcKalemMockMvc.perform(post("/api/dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaBorcKalem)))
            .andExpect(status().isBadRequest());

        // Validate the DosyaBorcKalem in the database
        List<DosyaBorcKalem> dosyaBorcKalemList = dosyaBorcKalemRepository.findAll();
        assertThat(dosyaBorcKalemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDosyaBorcKalems() throws Exception {
        // Initialize the database
        dosyaBorcKalemRepository.saveAndFlush(dosyaBorcKalem);

        // Get all the dosyaBorcKalemList
        restDosyaBorcKalemMockMvc.perform(get("/api/dosya-borc-kalems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosyaBorcKalem.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].orjinalBorcTutari").value(hasItem(DEFAULT_ORJINAL_BORC_TUTARI.intValue())));
    }

    @Test
    @Transactional
    public void getDosyaBorcKalem() throws Exception {
        // Initialize the database
        dosyaBorcKalemRepository.saveAndFlush(dosyaBorcKalem);

        // Get the dosyaBorcKalem
        restDosyaBorcKalemMockMvc.perform(get("/api/dosya-borc-kalems/{id}", dosyaBorcKalem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dosyaBorcKalem.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.orjinalBorcTutari").value(DEFAULT_ORJINAL_BORC_TUTARI.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDosyaBorcKalem() throws Exception {
        // Get the dosyaBorcKalem
        restDosyaBorcKalemMockMvc.perform(get("/api/dosya-borc-kalems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDosyaBorcKalem() throws Exception {
        // Initialize the database
        dosyaBorcKalemService.save(dosyaBorcKalem);

        int databaseSizeBeforeUpdate = dosyaBorcKalemRepository.findAll().size();

        // Update the dosyaBorcKalem
        DosyaBorcKalem updatedDosyaBorcKalem = dosyaBorcKalemRepository.findOne(dosyaBorcKalem.getId());
        // Disconnect from session so that the updates on updatedDosyaBorcKalem are not directly saved in db
        em.detach(updatedDosyaBorcKalem);
        updatedDosyaBorcKalem
            .kod(UPDATED_KOD)
            .orjinalBorcTutari(UPDATED_ORJINAL_BORC_TUTARI);

        restDosyaBorcKalemMockMvc.perform(put("/api/dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDosyaBorcKalem)))
            .andExpect(status().isOk());

        // Validate the DosyaBorcKalem in the database
        List<DosyaBorcKalem> dosyaBorcKalemList = dosyaBorcKalemRepository.findAll();
        assertThat(dosyaBorcKalemList).hasSize(databaseSizeBeforeUpdate);
        DosyaBorcKalem testDosyaBorcKalem = dosyaBorcKalemList.get(dosyaBorcKalemList.size() - 1);
        assertThat(testDosyaBorcKalem.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testDosyaBorcKalem.getOrjinalBorcTutari()).isEqualTo(UPDATED_ORJINAL_BORC_TUTARI);

        // Validate the DosyaBorcKalem in Elasticsearch
        DosyaBorcKalem dosyaBorcKalemEs = dosyaBorcKalemSearchRepository.findOne(testDosyaBorcKalem.getId());
        assertThat(dosyaBorcKalemEs).isEqualToIgnoringGivenFields(testDosyaBorcKalem);
    }

    @Test
    @Transactional
    public void updateNonExistingDosyaBorcKalem() throws Exception {
        int databaseSizeBeforeUpdate = dosyaBorcKalemRepository.findAll().size();

        // Create the DosyaBorcKalem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDosyaBorcKalemMockMvc.perform(put("/api/dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosyaBorcKalem)))
            .andExpect(status().isCreated());

        // Validate the DosyaBorcKalem in the database
        List<DosyaBorcKalem> dosyaBorcKalemList = dosyaBorcKalemRepository.findAll();
        assertThat(dosyaBorcKalemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDosyaBorcKalem() throws Exception {
        // Initialize the database
        dosyaBorcKalemService.save(dosyaBorcKalem);

        int databaseSizeBeforeDelete = dosyaBorcKalemRepository.findAll().size();

        // Get the dosyaBorcKalem
        restDosyaBorcKalemMockMvc.perform(delete("/api/dosya-borc-kalems/{id}", dosyaBorcKalem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dosyaBorcKalemExistsInEs = dosyaBorcKalemSearchRepository.exists(dosyaBorcKalem.getId());
        assertThat(dosyaBorcKalemExistsInEs).isFalse();

        // Validate the database is empty
        List<DosyaBorcKalem> dosyaBorcKalemList = dosyaBorcKalemRepository.findAll();
        assertThat(dosyaBorcKalemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDosyaBorcKalem() throws Exception {
        // Initialize the database
        dosyaBorcKalemService.save(dosyaBorcKalem);

        // Search the dosyaBorcKalem
        restDosyaBorcKalemMockMvc.perform(get("/api/_search/dosya-borc-kalems?query=id:" + dosyaBorcKalem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosyaBorcKalem.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].orjinalBorcTutari").value(hasItem(DEFAULT_ORJINAL_BORC_TUTARI.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DosyaBorcKalem.class);
        DosyaBorcKalem dosyaBorcKalem1 = new DosyaBorcKalem();
        dosyaBorcKalem1.setId(1L);
        DosyaBorcKalem dosyaBorcKalem2 = new DosyaBorcKalem();
        dosyaBorcKalem2.setId(dosyaBorcKalem1.getId());
        assertThat(dosyaBorcKalem1).isEqualTo(dosyaBorcKalem2);
        dosyaBorcKalem2.setId(2L);
        assertThat(dosyaBorcKalem1).isNotEqualTo(dosyaBorcKalem2);
        dosyaBorcKalem1.setId(null);
        assertThat(dosyaBorcKalem1).isNotEqualTo(dosyaBorcKalem2);
    }
}
