package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.BorcKalem;
import com.er.fin.repository.BorcKalemRepository;
import com.er.fin.service.BorcKalemService;
import com.er.fin.repository.search.BorcKalemSearchRepository;
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
 * Test class for the BorcKalemResource REST controller.
 *
 * @see BorcKalemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class BorcKalemResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private BorcKalemRepository borcKalemRepository;

    @Autowired
    private BorcKalemService borcKalemService;

    @Autowired
    private BorcKalemSearchRepository borcKalemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBorcKalemMockMvc;

    private BorcKalem borcKalem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BorcKalemResource borcKalemResource = new BorcKalemResource(borcKalemService);
        this.restBorcKalemMockMvc = MockMvcBuilders.standaloneSetup(borcKalemResource)
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
    public static BorcKalem createEntity(EntityManager em) {
        BorcKalem borcKalem = new BorcKalem()
            .kod(DEFAULT_KOD);
        return borcKalem;
    }

    @Before
    public void initTest() {
        borcKalemSearchRepository.deleteAll();
        borcKalem = createEntity(em);
    }

    @Test
    @Transactional
    public void createBorcKalem() throws Exception {
        int databaseSizeBeforeCreate = borcKalemRepository.findAll().size();

        // Create the BorcKalem
        restBorcKalemMockMvc.perform(post("/api/borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcKalem)))
            .andExpect(status().isCreated());

        // Validate the BorcKalem in the database
        List<BorcKalem> borcKalemList = borcKalemRepository.findAll();
        assertThat(borcKalemList).hasSize(databaseSizeBeforeCreate + 1);
        BorcKalem testBorcKalem = borcKalemList.get(borcKalemList.size() - 1);
        assertThat(testBorcKalem.getKod()).isEqualTo(DEFAULT_KOD);

        // Validate the BorcKalem in Elasticsearch
        BorcKalem borcKalemEs = borcKalemSearchRepository.findOne(testBorcKalem.getId());
        assertThat(borcKalemEs).isEqualToIgnoringGivenFields(testBorcKalem);
    }

    @Test
    @Transactional
    public void createBorcKalemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = borcKalemRepository.findAll().size();

        // Create the BorcKalem with an existing ID
        borcKalem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorcKalemMockMvc.perform(post("/api/borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcKalem)))
            .andExpect(status().isBadRequest());

        // Validate the BorcKalem in the database
        List<BorcKalem> borcKalemList = borcKalemRepository.findAll();
        assertThat(borcKalemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBorcKalems() throws Exception {
        // Initialize the database
        borcKalemRepository.saveAndFlush(borcKalem);

        // Get all the borcKalemList
        restBorcKalemMockMvc.perform(get("/api/borc-kalems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borcKalem.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getBorcKalem() throws Exception {
        // Initialize the database
        borcKalemRepository.saveAndFlush(borcKalem);

        // Get the borcKalem
        restBorcKalemMockMvc.perform(get("/api/borc-kalems/{id}", borcKalem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(borcKalem.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBorcKalem() throws Exception {
        // Get the borcKalem
        restBorcKalemMockMvc.perform(get("/api/borc-kalems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBorcKalem() throws Exception {
        // Initialize the database
        borcKalemService.save(borcKalem);

        int databaseSizeBeforeUpdate = borcKalemRepository.findAll().size();

        // Update the borcKalem
        BorcKalem updatedBorcKalem = borcKalemRepository.findOne(borcKalem.getId());
        // Disconnect from session so that the updates on updatedBorcKalem are not directly saved in db
        em.detach(updatedBorcKalem);
        updatedBorcKalem
            .kod(UPDATED_KOD);

        restBorcKalemMockMvc.perform(put("/api/borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBorcKalem)))
            .andExpect(status().isOk());

        // Validate the BorcKalem in the database
        List<BorcKalem> borcKalemList = borcKalemRepository.findAll();
        assertThat(borcKalemList).hasSize(databaseSizeBeforeUpdate);
        BorcKalem testBorcKalem = borcKalemList.get(borcKalemList.size() - 1);
        assertThat(testBorcKalem.getKod()).isEqualTo(UPDATED_KOD);

        // Validate the BorcKalem in Elasticsearch
        BorcKalem borcKalemEs = borcKalemSearchRepository.findOne(testBorcKalem.getId());
        assertThat(borcKalemEs).isEqualToIgnoringGivenFields(testBorcKalem);
    }

    @Test
    @Transactional
    public void updateNonExistingBorcKalem() throws Exception {
        int databaseSizeBeforeUpdate = borcKalemRepository.findAll().size();

        // Create the BorcKalem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBorcKalemMockMvc.perform(put("/api/borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borcKalem)))
            .andExpect(status().isCreated());

        // Validate the BorcKalem in the database
        List<BorcKalem> borcKalemList = borcKalemRepository.findAll();
        assertThat(borcKalemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBorcKalem() throws Exception {
        // Initialize the database
        borcKalemService.save(borcKalem);

        int databaseSizeBeforeDelete = borcKalemRepository.findAll().size();

        // Get the borcKalem
        restBorcKalemMockMvc.perform(delete("/api/borc-kalems/{id}", borcKalem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean borcKalemExistsInEs = borcKalemSearchRepository.exists(borcKalem.getId());
        assertThat(borcKalemExistsInEs).isFalse();

        // Validate the database is empty
        List<BorcKalem> borcKalemList = borcKalemRepository.findAll();
        assertThat(borcKalemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBorcKalem() throws Exception {
        // Initialize the database
        borcKalemService.save(borcKalem);

        // Search the borcKalem
        restBorcKalemMockMvc.perform(get("/api/_search/borc-kalems?query=id:" + borcKalem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borcKalem.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorcKalem.class);
        BorcKalem borcKalem1 = new BorcKalem();
        borcKalem1.setId(1L);
        BorcKalem borcKalem2 = new BorcKalem();
        borcKalem2.setId(borcKalem1.getId());
        assertThat(borcKalem1).isEqualTo(borcKalem2);
        borcKalem2.setId(2L);
        assertThat(borcKalem1).isNotEqualTo(borcKalem2);
        borcKalem1.setId(null);
        assertThat(borcKalem1).isNotEqualTo(borcKalem2);
    }
}
