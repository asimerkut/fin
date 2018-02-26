package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.HopDosya;
import com.er.fin.repository.HopDosyaRepository;
import com.er.fin.service.HopDosyaService;
import com.er.fin.repository.search.HopDosyaSearchRepository;
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

import com.er.fin.domain.enumeration.EnmDosyaTipi;
/**
 * Test class for the HopDosyaResource REST controller.
 *
 * @see HopDosyaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class HopDosyaResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final EnmDosyaTipi DEFAULT_DOSYA_TIPI = EnmDosyaTipi.ICRA;
    private static final EnmDosyaTipi UPDATED_DOSYA_TIPI = EnmDosyaTipi.DAVA;

    @Autowired
    private HopDosyaRepository hopDosyaRepository;

    @Autowired
    private HopDosyaService hopDosyaService;

    @Autowired
    private HopDosyaSearchRepository hopDosyaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHopDosyaMockMvc;

    private HopDosya hopDosya;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HopDosyaResource hopDosyaResource = new HopDosyaResource(hopDosyaService);
        this.restHopDosyaMockMvc = MockMvcBuilders.standaloneSetup(hopDosyaResource)
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
    public static HopDosya createEntity(EntityManager em) {
        HopDosya hopDosya = new HopDosya()
            .kod(DEFAULT_KOD)
            .dosyaTipi(DEFAULT_DOSYA_TIPI);
        return hopDosya;
    }

    @Before
    public void initTest() {
        hopDosyaSearchRepository.deleteAll();
        hopDosya = createEntity(em);
    }

    @Test
    @Transactional
    public void createHopDosya() throws Exception {
        int databaseSizeBeforeCreate = hopDosyaRepository.findAll().size();

        // Create the HopDosya
        restHopDosyaMockMvc.perform(post("/api/hop-dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosya)))
            .andExpect(status().isCreated());

        // Validate the HopDosya in the database
        List<HopDosya> hopDosyaList = hopDosyaRepository.findAll();
        assertThat(hopDosyaList).hasSize(databaseSizeBeforeCreate + 1);
        HopDosya testHopDosya = hopDosyaList.get(hopDosyaList.size() - 1);
        assertThat(testHopDosya.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testHopDosya.getDosyaTipi()).isEqualTo(DEFAULT_DOSYA_TIPI);

        // Validate the HopDosya in Elasticsearch
        HopDosya hopDosyaEs = hopDosyaSearchRepository.findOne(testHopDosya.getId());
        assertThat(hopDosyaEs).isEqualToIgnoringGivenFields(testHopDosya);
    }

    @Test
    @Transactional
    public void createHopDosyaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hopDosyaRepository.findAll().size();

        // Create the HopDosya with an existing ID
        hopDosya.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHopDosyaMockMvc.perform(post("/api/hop-dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosya)))
            .andExpect(status().isBadRequest());

        // Validate the HopDosya in the database
        List<HopDosya> hopDosyaList = hopDosyaRepository.findAll();
        assertThat(hopDosyaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHopDosyas() throws Exception {
        // Initialize the database
        hopDosyaRepository.saveAndFlush(hopDosya);

        // Get all the hopDosyaList
        restHopDosyaMockMvc.perform(get("/api/hop-dosyas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopDosya.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].dosyaTipi").value(hasItem(DEFAULT_DOSYA_TIPI.toString())));
    }

    @Test
    @Transactional
    public void getHopDosya() throws Exception {
        // Initialize the database
        hopDosyaRepository.saveAndFlush(hopDosya);

        // Get the hopDosya
        restHopDosyaMockMvc.perform(get("/api/hop-dosyas/{id}", hopDosya.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hopDosya.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.dosyaTipi").value(DEFAULT_DOSYA_TIPI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHopDosya() throws Exception {
        // Get the hopDosya
        restHopDosyaMockMvc.perform(get("/api/hop-dosyas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHopDosya() throws Exception {
        // Initialize the database
        hopDosyaService.save(hopDosya);

        int databaseSizeBeforeUpdate = hopDosyaRepository.findAll().size();

        // Update the hopDosya
        HopDosya updatedHopDosya = hopDosyaRepository.findOne(hopDosya.getId());
        // Disconnect from session so that the updates on updatedHopDosya are not directly saved in db
        em.detach(updatedHopDosya);
        updatedHopDosya
            .kod(UPDATED_KOD)
            .dosyaTipi(UPDATED_DOSYA_TIPI);

        restHopDosyaMockMvc.perform(put("/api/hop-dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHopDosya)))
            .andExpect(status().isOk());

        // Validate the HopDosya in the database
        List<HopDosya> hopDosyaList = hopDosyaRepository.findAll();
        assertThat(hopDosyaList).hasSize(databaseSizeBeforeUpdate);
        HopDosya testHopDosya = hopDosyaList.get(hopDosyaList.size() - 1);
        assertThat(testHopDosya.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testHopDosya.getDosyaTipi()).isEqualTo(UPDATED_DOSYA_TIPI);

        // Validate the HopDosya in Elasticsearch
        HopDosya hopDosyaEs = hopDosyaSearchRepository.findOne(testHopDosya.getId());
        assertThat(hopDosyaEs).isEqualToIgnoringGivenFields(testHopDosya);
    }

    @Test
    @Transactional
    public void updateNonExistingHopDosya() throws Exception {
        int databaseSizeBeforeUpdate = hopDosyaRepository.findAll().size();

        // Create the HopDosya

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHopDosyaMockMvc.perform(put("/api/hop-dosyas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosya)))
            .andExpect(status().isCreated());

        // Validate the HopDosya in the database
        List<HopDosya> hopDosyaList = hopDosyaRepository.findAll();
        assertThat(hopDosyaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHopDosya() throws Exception {
        // Initialize the database
        hopDosyaService.save(hopDosya);

        int databaseSizeBeforeDelete = hopDosyaRepository.findAll().size();

        // Get the hopDosya
        restHopDosyaMockMvc.perform(delete("/api/hop-dosyas/{id}", hopDosya.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hopDosyaExistsInEs = hopDosyaSearchRepository.exists(hopDosya.getId());
        assertThat(hopDosyaExistsInEs).isFalse();

        // Validate the database is empty
        List<HopDosya> hopDosyaList = hopDosyaRepository.findAll();
        assertThat(hopDosyaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHopDosya() throws Exception {
        // Initialize the database
        hopDosyaService.save(hopDosya);

        // Search the hopDosya
        restHopDosyaMockMvc.perform(get("/api/_search/hop-dosyas?query=id:" + hopDosya.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopDosya.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].dosyaTipi").value(hasItem(DEFAULT_DOSYA_TIPI.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopDosya.class);
        HopDosya hopDosya1 = new HopDosya();
        hopDosya1.setId(1L);
        HopDosya hopDosya2 = new HopDosya();
        hopDosya2.setId(hopDosya1.getId());
        assertThat(hopDosya1).isEqualTo(hopDosya2);
        hopDosya2.setId(2L);
        assertThat(hopDosya1).isNotEqualTo(hopDosya2);
        hopDosya1.setId(null);
        assertThat(hopDosya1).isNotEqualTo(hopDosya2);
    }
}
