package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.Dosya;
import com.er.fin.repository.DosyaRepository;
import com.er.fin.service.DosyaService;
import com.er.fin.repository.search.DosyaSearchRepository;
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
 * Test class for the DosyaResource REST controller.
 *
 * @see DosyaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DosyaResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final String DEFAULT_DOSYA_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOSYA_NO = "BBBBBBBBBB";

    @Autowired
    private DosyaRepository dosyaRepository;

    @Autowired
    private DosyaService dosyaService;

    @Autowired
    private DosyaSearchRepository dosyaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDosyaMockMvc;

    private Dosya dosya;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DosyaResource dosyaResource = new DosyaResource(dosyaService);
        this.restDosyaMockMvc = MockMvcBuilders.standaloneSetup(dosyaResource)
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
    public static Dosya createEntity(EntityManager em) {
        Dosya dosya = new Dosya()
            .kod(DEFAULT_KOD)
            .dosyaNo(DEFAULT_DOSYA_NO);
        return dosya;
    }

    @Before
    public void initTest() {
        dosyaSearchRepository.deleteAll();
        dosya = createEntity(em);
    }

    @Test
    @Transactional
    public void createDosya() throws Exception {
        int databaseSizeBeforeCreate = dosyaRepository.findAll().size();

        // Create the Dosya
        restDosyaMockMvc.perform(post("/api/dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosya)))
            .andExpect(status().isCreated());

        // Validate the Dosya in the database
        List<Dosya> dosyaList = dosyaRepository.findAll();
        assertThat(dosyaList).hasSize(databaseSizeBeforeCreate + 1);
        Dosya testDosya = dosyaList.get(dosyaList.size() - 1);
        assertThat(testDosya.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testDosya.getDosyaNo()).isEqualTo(DEFAULT_DOSYA_NO);

        // Validate the Dosya in Elasticsearch
        Dosya dosyaEs = dosyaSearchRepository.findOne(testDosya.getId());
        assertThat(dosyaEs).isEqualToIgnoringGivenFields(testDosya);
    }

    @Test
    @Transactional
    public void createDosyaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dosyaRepository.findAll().size();

        // Create the Dosya with an existing ID
        dosya.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosyaMockMvc.perform(post("/api/dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosya)))
            .andExpect(status().isBadRequest());

        // Validate the Dosya in the database
        List<Dosya> dosyaList = dosyaRepository.findAll();
        assertThat(dosyaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDosyas() throws Exception {
        // Initialize the database
        dosyaRepository.saveAndFlush(dosya);

        // Get all the dosyaList
        restDosyaMockMvc.perform(get("/api/dosyas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosya.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].dosyaNo").value(hasItem(DEFAULT_DOSYA_NO.toString())));
    }

    @Test
    @Transactional
    public void getDosya() throws Exception {
        // Initialize the database
        dosyaRepository.saveAndFlush(dosya);

        // Get the dosya
        restDosyaMockMvc.perform(get("/api/dosyas/{id}", dosya.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dosya.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.dosyaNo").value(DEFAULT_DOSYA_NO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDosya() throws Exception {
        // Get the dosya
        restDosyaMockMvc.perform(get("/api/dosyas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDosya() throws Exception {
        // Initialize the database
        dosyaService.save(dosya);

        int databaseSizeBeforeUpdate = dosyaRepository.findAll().size();

        // Update the dosya
        Dosya updatedDosya = dosyaRepository.findOne(dosya.getId());
        // Disconnect from session so that the updates on updatedDosya are not directly saved in db
        em.detach(updatedDosya);
        updatedDosya
            .kod(UPDATED_KOD)
            .dosyaNo(UPDATED_DOSYA_NO);

        restDosyaMockMvc.perform(put("/api/dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDosya)))
            .andExpect(status().isOk());

        // Validate the Dosya in the database
        List<Dosya> dosyaList = dosyaRepository.findAll();
        assertThat(dosyaList).hasSize(databaseSizeBeforeUpdate);
        Dosya testDosya = dosyaList.get(dosyaList.size() - 1);
        assertThat(testDosya.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testDosya.getDosyaNo()).isEqualTo(UPDATED_DOSYA_NO);

        // Validate the Dosya in Elasticsearch
        Dosya dosyaEs = dosyaSearchRepository.findOne(testDosya.getId());
        assertThat(dosyaEs).isEqualToIgnoringGivenFields(testDosya);
    }

    @Test
    @Transactional
    public void updateNonExistingDosya() throws Exception {
        int databaseSizeBeforeUpdate = dosyaRepository.findAll().size();

        // Create the Dosya

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDosyaMockMvc.perform(put("/api/dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosya)))
            .andExpect(status().isCreated());

        // Validate the Dosya in the database
        List<Dosya> dosyaList = dosyaRepository.findAll();
        assertThat(dosyaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDosya() throws Exception {
        // Initialize the database
        dosyaService.save(dosya);

        int databaseSizeBeforeDelete = dosyaRepository.findAll().size();

        // Get the dosya
        restDosyaMockMvc.perform(delete("/api/dosyas/{id}", dosya.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dosyaExistsInEs = dosyaSearchRepository.exists(dosya.getId());
        assertThat(dosyaExistsInEs).isFalse();

        // Validate the database is empty
        List<Dosya> dosyaList = dosyaRepository.findAll();
        assertThat(dosyaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDosya() throws Exception {
        // Initialize the database
        dosyaService.save(dosya);

        // Search the dosya
        restDosyaMockMvc.perform(get("/api/_search/dosyas?query=id:" + dosya.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosya.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].dosyaNo").value(hasItem(DEFAULT_DOSYA_NO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dosya.class);
        Dosya dosya1 = new Dosya();
        dosya1.setId(1L);
        Dosya dosya2 = new Dosya();
        dosya2.setId(dosya1.getId());
        assertThat(dosya1).isEqualTo(dosya2);
        dosya2.setId(2L);
        assertThat(dosya1).isNotEqualTo(dosya2);
        dosya1.setId(null);
        assertThat(dosya1).isNotEqualTo(dosya2);
    }
}
