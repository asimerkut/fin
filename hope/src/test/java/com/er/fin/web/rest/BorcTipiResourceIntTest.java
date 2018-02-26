package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.BorcTipi;
import com.er.fin.repository.BorcTipiRepository;
import com.er.fin.service.BorcTipiService;
import com.er.fin.repository.search.BorcTipiSearchRepository;
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
 * Test class for the BorcTipiResource REST controller.
 *
 * @see BorcTipiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class BorcTipiResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private BorcTipiRepository borcTipiRepository;

    @Autowired
    private BorcTipiService borcTipiService;

    @Autowired
    private BorcTipiSearchRepository borcTipiSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBorcTipiMockMvc;

    private BorcTipi borcTipi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BorcTipiResource borcTipiResource = new BorcTipiResource(borcTipiService);
        this.restBorcTipiMockMvc = MockMvcBuilders.standaloneSetup(borcTipiResource)
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
    public static BorcTipi createEntity(EntityManager em) {
        BorcTipi borcTipi = new BorcTipi()
            .kod(DEFAULT_KOD);
        return borcTipi;
    }

    @Before
    public void initTest() {
        borcTipiSearchRepository.deleteAll();
        borcTipi = createEntity(em);
    }

    @Test
    @Transactional
    public void createBorcTipi() throws Exception {
        int databaseSizeBeforeCreate = borcTipiRepository.findAll().size();

        // Create the BorcTipi
        restBorcTipiMockMvc.perform(post("/api/borc-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcTipi)))
            .andExpect(status().isCreated());

        // Validate the BorcTipi in the database
        List<BorcTipi> borcTipiList = borcTipiRepository.findAll();
        assertThat(borcTipiList).hasSize(databaseSizeBeforeCreate + 1);
        BorcTipi testBorcTipi = borcTipiList.get(borcTipiList.size() - 1);
        assertThat(testBorcTipi.getKod()).isEqualTo(DEFAULT_KOD);

        // Validate the BorcTipi in Elasticsearch
        BorcTipi borcTipiEs = borcTipiSearchRepository.findOne(testBorcTipi.getId());
        assertThat(borcTipiEs).isEqualToIgnoringGivenFields(testBorcTipi);
    }

    @Test
    @Transactional
    public void createBorcTipiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = borcTipiRepository.findAll().size();

        // Create the BorcTipi with an existing ID
        borcTipi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorcTipiMockMvc.perform(post("/api/borc-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcTipi)))
            .andExpect(status().isBadRequest());

        // Validate the BorcTipi in the database
        List<BorcTipi> borcTipiList = borcTipiRepository.findAll();
        assertThat(borcTipiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBorcTipis() throws Exception {
        // Initialize the database
        borcTipiRepository.saveAndFlush(borcTipi);

        // Get all the borcTipiList
        restBorcTipiMockMvc.perform(get("/api/borc-tipis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borcTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getBorcTipi() throws Exception {
        // Initialize the database
        borcTipiRepository.saveAndFlush(borcTipi);

        // Get the borcTipi
        restBorcTipiMockMvc.perform(get("/api/borc-tipis/{id}", borcTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(borcTipi.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBorcTipi() throws Exception {
        // Get the borcTipi
        restBorcTipiMockMvc.perform(get("/api/borc-tipis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBorcTipi() throws Exception {
        // Initialize the database
        borcTipiService.save(borcTipi);

        int databaseSizeBeforeUpdate = borcTipiRepository.findAll().size();

        // Update the borcTipi
        BorcTipi updatedBorcTipi = borcTipiRepository.findOne(borcTipi.getId());
        // Disconnect from session so that the updates on updatedBorcTipi are not directly saved in db
        em.detach(updatedBorcTipi);
        updatedBorcTipi
            .kod(UPDATED_KOD);

        restBorcTipiMockMvc.perform(put("/api/borc-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBorcTipi)))
            .andExpect(status().isOk());

        // Validate the BorcTipi in the database
        List<BorcTipi> borcTipiList = borcTipiRepository.findAll();
        assertThat(borcTipiList).hasSize(databaseSizeBeforeUpdate);
        BorcTipi testBorcTipi = borcTipiList.get(borcTipiList.size() - 1);
        assertThat(testBorcTipi.getKod()).isEqualTo(UPDATED_KOD);

        // Validate the BorcTipi in Elasticsearch
        BorcTipi borcTipiEs = borcTipiSearchRepository.findOne(testBorcTipi.getId());
        assertThat(borcTipiEs).isEqualToIgnoringGivenFields(testBorcTipi);
    }

    @Test
    @Transactional
    public void updateNonExistingBorcTipi() throws Exception {
        int databaseSizeBeforeUpdate = borcTipiRepository.findAll().size();

        // Create the BorcTipi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBorcTipiMockMvc.perform(put("/api/borc-tipis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcTipi)))
            .andExpect(status().isCreated());

        // Validate the BorcTipi in the database
        List<BorcTipi> borcTipiList = borcTipiRepository.findAll();
        assertThat(borcTipiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBorcTipi() throws Exception {
        // Initialize the database
        borcTipiService.save(borcTipi);

        int databaseSizeBeforeDelete = borcTipiRepository.findAll().size();

        // Get the borcTipi
        restBorcTipiMockMvc.perform(delete("/api/borc-tipis/{id}", borcTipi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean borcTipiExistsInEs = borcTipiSearchRepository.exists(borcTipi.getId());
        assertThat(borcTipiExistsInEs).isFalse();

        // Validate the database is empty
        List<BorcTipi> borcTipiList = borcTipiRepository.findAll();
        assertThat(borcTipiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBorcTipi() throws Exception {
        // Initialize the database
        borcTipiService.save(borcTipi);

        // Search the borcTipi
        restBorcTipiMockMvc.perform(get("/api/_search/borc-tipis?query=id:" + borcTipi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borcTipi.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorcTipi.class);
        BorcTipi borcTipi1 = new BorcTipi();
        borcTipi1.setId(1L);
        BorcTipi borcTipi2 = new BorcTipi();
        borcTipi2.setId(borcTipi1.getId());
        assertThat(borcTipi1).isEqualTo(borcTipi2);
        borcTipi2.setId(2L);
        assertThat(borcTipi1).isNotEqualTo(borcTipi2);
        borcTipi1.setId(null);
        assertThat(borcTipi1).isNotEqualTo(borcTipi2);
    }
}
