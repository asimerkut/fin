package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.BorcGrubu;
import com.er.fin.repository.BorcGrubuRepository;
import com.er.fin.service.BorcGrubuService;
import com.er.fin.repository.search.BorcGrubuSearchRepository;
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
 * Test class for the BorcGrubuResource REST controller.
 *
 * @see BorcGrubuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class BorcGrubuResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private BorcGrubuRepository borcGrubuRepository;

    @Autowired
    private BorcGrubuService borcGrubuService;

    @Autowired
    private BorcGrubuSearchRepository borcGrubuSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBorcGrubuMockMvc;

    private BorcGrubu borcGrubu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BorcGrubuResource borcGrubuResource = new BorcGrubuResource(borcGrubuService);
        this.restBorcGrubuMockMvc = MockMvcBuilders.standaloneSetup(borcGrubuResource)
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
    public static BorcGrubu createEntity(EntityManager em) {
        BorcGrubu borcGrubu = new BorcGrubu()
            .kod(DEFAULT_KOD);
        return borcGrubu;
    }

    @Before
    public void initTest() {
        borcGrubuSearchRepository.deleteAll();
        borcGrubu = createEntity(em);
    }

    @Test
    @Transactional
    public void createBorcGrubu() throws Exception {
        int databaseSizeBeforeCreate = borcGrubuRepository.findAll().size();

        // Create the BorcGrubu
        restBorcGrubuMockMvc.perform(post("/api/borc-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcGrubu)))
            .andExpect(status().isCreated());

        // Validate the BorcGrubu in the database
        List<BorcGrubu> borcGrubuList = borcGrubuRepository.findAll();
        assertThat(borcGrubuList).hasSize(databaseSizeBeforeCreate + 1);
        BorcGrubu testBorcGrubu = borcGrubuList.get(borcGrubuList.size() - 1);
        assertThat(testBorcGrubu.getKod()).isEqualTo(DEFAULT_KOD);

        // Validate the BorcGrubu in Elasticsearch
        BorcGrubu borcGrubuEs = borcGrubuSearchRepository.findOne(testBorcGrubu.getId());
        assertThat(borcGrubuEs).isEqualToIgnoringGivenFields(testBorcGrubu);
    }

    @Test
    @Transactional
    public void createBorcGrubuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = borcGrubuRepository.findAll().size();

        // Create the BorcGrubu with an existing ID
        borcGrubu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorcGrubuMockMvc.perform(post("/api/borc-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcGrubu)))
            .andExpect(status().isBadRequest());

        // Validate the BorcGrubu in the database
        List<BorcGrubu> borcGrubuList = borcGrubuRepository.findAll();
        assertThat(borcGrubuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBorcGrubus() throws Exception {
        // Initialize the database
        borcGrubuRepository.saveAndFlush(borcGrubu);

        // Get all the borcGrubuList
        restBorcGrubuMockMvc.perform(get("/api/borc-grubus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borcGrubu.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getBorcGrubu() throws Exception {
        // Initialize the database
        borcGrubuRepository.saveAndFlush(borcGrubu);

        // Get the borcGrubu
        restBorcGrubuMockMvc.perform(get("/api/borc-grubus/{id}", borcGrubu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(borcGrubu.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBorcGrubu() throws Exception {
        // Get the borcGrubu
        restBorcGrubuMockMvc.perform(get("/api/borc-grubus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBorcGrubu() throws Exception {
        // Initialize the database
        borcGrubuService.save(borcGrubu);

        int databaseSizeBeforeUpdate = borcGrubuRepository.findAll().size();

        // Update the borcGrubu
        BorcGrubu updatedBorcGrubu = borcGrubuRepository.findOne(borcGrubu.getId());
        // Disconnect from session so that the updates on updatedBorcGrubu are not directly saved in db
        em.detach(updatedBorcGrubu);
        updatedBorcGrubu
            .kod(UPDATED_KOD);

        restBorcGrubuMockMvc.perform(put("/api/borc-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBorcGrubu)))
            .andExpect(status().isOk());

        // Validate the BorcGrubu in the database
        List<BorcGrubu> borcGrubuList = borcGrubuRepository.findAll();
        assertThat(borcGrubuList).hasSize(databaseSizeBeforeUpdate);
        BorcGrubu testBorcGrubu = borcGrubuList.get(borcGrubuList.size() - 1);
        assertThat(testBorcGrubu.getKod()).isEqualTo(UPDATED_KOD);

        // Validate the BorcGrubu in Elasticsearch
        BorcGrubu borcGrubuEs = borcGrubuSearchRepository.findOne(testBorcGrubu.getId());
        assertThat(borcGrubuEs).isEqualToIgnoringGivenFields(testBorcGrubu);
    }

    @Test
    @Transactional
    public void updateNonExistingBorcGrubu() throws Exception {
        int databaseSizeBeforeUpdate = borcGrubuRepository.findAll().size();

        // Create the BorcGrubu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBorcGrubuMockMvc.perform(put("/api/borc-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcGrubu)))
            .andExpect(status().isCreated());

        // Validate the BorcGrubu in the database
        List<BorcGrubu> borcGrubuList = borcGrubuRepository.findAll();
        assertThat(borcGrubuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBorcGrubu() throws Exception {
        // Initialize the database
        borcGrubuService.save(borcGrubu);

        int databaseSizeBeforeDelete = borcGrubuRepository.findAll().size();

        // Get the borcGrubu
        restBorcGrubuMockMvc.perform(delete("/api/borc-grubus/{id}", borcGrubu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean borcGrubuExistsInEs = borcGrubuSearchRepository.exists(borcGrubu.getId());
        assertThat(borcGrubuExistsInEs).isFalse();

        // Validate the database is empty
        List<BorcGrubu> borcGrubuList = borcGrubuRepository.findAll();
        assertThat(borcGrubuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBorcGrubu() throws Exception {
        // Initialize the database
        borcGrubuService.save(borcGrubu);

        // Search the borcGrubu
        restBorcGrubuMockMvc.perform(get("/api/_search/borc-grubus?query=id:" + borcGrubu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borcGrubu.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorcGrubu.class);
        BorcGrubu borcGrubu1 = new BorcGrubu();
        borcGrubu1.setId(1L);
        BorcGrubu borcGrubu2 = new BorcGrubu();
        borcGrubu2.setId(borcGrubu1.getId());
        assertThat(borcGrubu1).isEqualTo(borcGrubu2);
        borcGrubu2.setId(2L);
        assertThat(borcGrubu1).isNotEqualTo(borcGrubu2);
        borcGrubu1.setId(null);
        assertThat(borcGrubu1).isNotEqualTo(borcGrubu2);
    }
}
