package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.MasrafTipi;
import com.er.fin.repository.MasrafTipiRepository;
import com.er.fin.service.MasrafTipiService;
import com.er.fin.repository.search.MasrafTipiSearchRepository;
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
 * Test class for the MasrafTipiResource REST controller.
 *
 * @see MasrafTipiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class MasrafTipiResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private MasrafTipiRepository masrafTipiRepository;

    @Autowired
    private MasrafTipiService masrafTipiService;

    @Autowired
    private MasrafTipiSearchRepository masrafTipiSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMasrafTipiMockMvc;

    private MasrafTipi masrafTipi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MasrafTipiResource masrafTipiResource = new MasrafTipiResource(masrafTipiService);
        this.restMasrafTipiMockMvc = MockMvcBuilders.standaloneSetup(masrafTipiResource)
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
    public static MasrafTipi createEntity(EntityManager em) {
        MasrafTipi masrafTipi = new MasrafTipi()
            .kod(DEFAULT_KOD);
        return masrafTipi;
    }

    @Before
    public void initTest() {
        masrafTipiSearchRepository.deleteAll();
        masrafTipi = createEntity(em);
    }

    @Test
    @Transactional
    public void createMasrafTipi() throws Exception {
        int databaseSizeBeforeCreate = masrafTipiRepository.findAll().size();

        // Create the MasrafTipi
        restMasrafTipiMockMvc.perform(post("/api/masraf-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masrafTipi)))
            .andExpect(status().isCreated());

        // Validate the MasrafTipi in the database
        List<MasrafTipi> masrafTipiList = masrafTipiRepository.findAll();
        assertThat(masrafTipiList).hasSize(databaseSizeBeforeCreate + 1);
        MasrafTipi testMasrafTipi = masrafTipiList.get(masrafTipiList.size() - 1);
        assertThat(testMasrafTipi.getKod()).isEqualTo(DEFAULT_KOD);

        // Validate the MasrafTipi in Elasticsearch
        MasrafTipi masrafTipiEs = masrafTipiSearchRepository.findOne(testMasrafTipi.getId());
        assertThat(masrafTipiEs).isEqualToIgnoringGivenFields(testMasrafTipi);
    }

    @Test
    @Transactional
    public void createMasrafTipiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = masrafTipiRepository.findAll().size();

        // Create the MasrafTipi with an existing ID
        masrafTipi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMasrafTipiMockMvc.perform(post("/api/masraf-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masrafTipi)))
            .andExpect(status().isBadRequest());

        // Validate the MasrafTipi in the database
        List<MasrafTipi> masrafTipiList = masrafTipiRepository.findAll();
        assertThat(masrafTipiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMasrafTipis() throws Exception {
        // Initialize the database
        masrafTipiRepository.saveAndFlush(masrafTipi);

        // Get all the masrafTipiList
        restMasrafTipiMockMvc.perform(get("/api/masraf-tipis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masrafTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getMasrafTipi() throws Exception {
        // Initialize the database
        masrafTipiRepository.saveAndFlush(masrafTipi);

        // Get the masrafTipi
        restMasrafTipiMockMvc.perform(get("/api/masraf-tipis/{id}", masrafTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(masrafTipi.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMasrafTipi() throws Exception {
        // Get the masrafTipi
        restMasrafTipiMockMvc.perform(get("/api/masraf-tipis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMasrafTipi() throws Exception {
        // Initialize the database
        masrafTipiService.save(masrafTipi);

        int databaseSizeBeforeUpdate = masrafTipiRepository.findAll().size();

        // Update the masrafTipi
        MasrafTipi updatedMasrafTipi = masrafTipiRepository.findOne(masrafTipi.getId());
        // Disconnect from session so that the updates on updatedMasrafTipi are not directly saved in db
        em.detach(updatedMasrafTipi);
        updatedMasrafTipi
            .kod(UPDATED_KOD);

        restMasrafTipiMockMvc.perform(put("/api/masraf-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMasrafTipi)))
            .andExpect(status().isOk());

        // Validate the MasrafTipi in the database
        List<MasrafTipi> masrafTipiList = masrafTipiRepository.findAll();
        assertThat(masrafTipiList).hasSize(databaseSizeBeforeUpdate);
        MasrafTipi testMasrafTipi = masrafTipiList.get(masrafTipiList.size() - 1);
        assertThat(testMasrafTipi.getKod()).isEqualTo(UPDATED_KOD);

        // Validate the MasrafTipi in Elasticsearch
        MasrafTipi masrafTipiEs = masrafTipiSearchRepository.findOne(testMasrafTipi.getId());
        assertThat(masrafTipiEs).isEqualToIgnoringGivenFields(testMasrafTipi);
    }

    @Test
    @Transactional
    public void updateNonExistingMasrafTipi() throws Exception {
        int databaseSizeBeforeUpdate = masrafTipiRepository.findAll().size();

        // Create the MasrafTipi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMasrafTipiMockMvc.perform(put("/api/masraf-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masrafTipi)))
            .andExpect(status().isCreated());

        // Validate the MasrafTipi in the database
        List<MasrafTipi> masrafTipiList = masrafTipiRepository.findAll();
        assertThat(masrafTipiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMasrafTipi() throws Exception {
        // Initialize the database
        masrafTipiService.save(masrafTipi);

        int databaseSizeBeforeDelete = masrafTipiRepository.findAll().size();

        // Get the masrafTipi
        restMasrafTipiMockMvc.perform(delete("/api/masraf-tipis/{id}", masrafTipi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean masrafTipiExistsInEs = masrafTipiSearchRepository.exists(masrafTipi.getId());
        assertThat(masrafTipiExistsInEs).isFalse();

        // Validate the database is empty
        List<MasrafTipi> masrafTipiList = masrafTipiRepository.findAll();
        assertThat(masrafTipiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMasrafTipi() throws Exception {
        // Initialize the database
        masrafTipiService.save(masrafTipi);

        // Search the masrafTipi
        restMasrafTipiMockMvc.perform(get("/api/_search/masraf-tipis?query=id:" + masrafTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masrafTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasrafTipi.class);
        MasrafTipi masrafTipi1 = new MasrafTipi();
        masrafTipi1.setId(1L);
        MasrafTipi masrafTipi2 = new MasrafTipi();
        masrafTipi2.setId(masrafTipi1.getId());
        assertThat(masrafTipi1).isEqualTo(masrafTipi2);
        masrafTipi2.setId(2L);
        assertThat(masrafTipi1).isNotEqualTo(masrafTipi2);
        masrafTipi1.setId(null);
        assertThat(masrafTipi1).isNotEqualTo(masrafTipi2);
    }
}
