package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.HopDosyaBorcKalem;
import com.er.fin.repository.HopDosyaBorcKalemRepository;
import com.er.fin.service.HopDosyaBorcKalemService;
import com.er.fin.repository.search.HopDosyaBorcKalemSearchRepository;
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
 * Test class for the HopDosyaBorcKalemResource REST controller.
 *
 * @see HopDosyaBorcKalemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class HopDosyaBorcKalemResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_TUTAR = new BigDecimal(2);

    @Autowired
    private HopDosyaBorcKalemRepository hopDosyaBorcKalemRepository;

    @Autowired
    private HopDosyaBorcKalemService hopDosyaBorcKalemService;

    @Autowired
    private HopDosyaBorcKalemSearchRepository hopDosyaBorcKalemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHopDosyaBorcKalemMockMvc;

    private HopDosyaBorcKalem hopDosyaBorcKalem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HopDosyaBorcKalemResource hopDosyaBorcKalemResource = new HopDosyaBorcKalemResource(hopDosyaBorcKalemService);
        this.restHopDosyaBorcKalemMockMvc = MockMvcBuilders.standaloneSetup(hopDosyaBorcKalemResource)
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
    public static HopDosyaBorcKalem createEntity(EntityManager em) {
        HopDosyaBorcKalem hopDosyaBorcKalem = new HopDosyaBorcKalem()
            .kod(DEFAULT_KOD)
            .tutar(DEFAULT_TUTAR);
        return hopDosyaBorcKalem;
    }

    @Before
    public void initTest() {
        hopDosyaBorcKalemSearchRepository.deleteAll();
        hopDosyaBorcKalem = createEntity(em);
    }

    @Test
    @Transactional
    public void createHopDosyaBorcKalem() throws Exception {
        int databaseSizeBeforeCreate = hopDosyaBorcKalemRepository.findAll().size();

        // Create the HopDosyaBorcKalem
        restHopDosyaBorcKalemMockMvc.perform(post("/api/hop-dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosyaBorcKalem)))
            .andExpect(status().isCreated());

        // Validate the HopDosyaBorcKalem in the database
        List<HopDosyaBorcKalem> hopDosyaBorcKalemList = hopDosyaBorcKalemRepository.findAll();
        assertThat(hopDosyaBorcKalemList).hasSize(databaseSizeBeforeCreate + 1);
        HopDosyaBorcKalem testHopDosyaBorcKalem = hopDosyaBorcKalemList.get(hopDosyaBorcKalemList.size() - 1);
        assertThat(testHopDosyaBorcKalem.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testHopDosyaBorcKalem.getTutar()).isEqualTo(DEFAULT_TUTAR);

        // Validate the HopDosyaBorcKalem in Elasticsearch
        HopDosyaBorcKalem hopDosyaBorcKalemEs = hopDosyaBorcKalemSearchRepository.findOne(testHopDosyaBorcKalem.getId());
        assertThat(hopDosyaBorcKalemEs).isEqualToIgnoringGivenFields(testHopDosyaBorcKalem);
    }

    @Test
    @Transactional
    public void createHopDosyaBorcKalemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hopDosyaBorcKalemRepository.findAll().size();

        // Create the HopDosyaBorcKalem with an existing ID
        hopDosyaBorcKalem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHopDosyaBorcKalemMockMvc.perform(post("/api/hop-dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosyaBorcKalem)))
            .andExpect(status().isBadRequest());

        // Validate the HopDosyaBorcKalem in the database
        List<HopDosyaBorcKalem> hopDosyaBorcKalemList = hopDosyaBorcKalemRepository.findAll();
        assertThat(hopDosyaBorcKalemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHopDosyaBorcKalems() throws Exception {
        // Initialize the database
        hopDosyaBorcKalemRepository.saveAndFlush(hopDosyaBorcKalem);

        // Get all the hopDosyaBorcKalemList
        restHopDosyaBorcKalemMockMvc.perform(get("/api/hop-dosya-borc-kalems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopDosyaBorcKalem.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())));
    }

    @Test
    @Transactional
    public void getHopDosyaBorcKalem() throws Exception {
        // Initialize the database
        hopDosyaBorcKalemRepository.saveAndFlush(hopDosyaBorcKalem);

        // Get the hopDosyaBorcKalem
        restHopDosyaBorcKalemMockMvc.perform(get("/api/hop-dosya-borc-kalems/{id}", hopDosyaBorcKalem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hopDosyaBorcKalem.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.tutar").value(DEFAULT_TUTAR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHopDosyaBorcKalem() throws Exception {
        // Get the hopDosyaBorcKalem
        restHopDosyaBorcKalemMockMvc.perform(get("/api/hop-dosya-borc-kalems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHopDosyaBorcKalem() throws Exception {
        // Initialize the database
        hopDosyaBorcKalemService.save(hopDosyaBorcKalem);

        int databaseSizeBeforeUpdate = hopDosyaBorcKalemRepository.findAll().size();

        // Update the hopDosyaBorcKalem
        HopDosyaBorcKalem updatedHopDosyaBorcKalem = hopDosyaBorcKalemRepository.findOne(hopDosyaBorcKalem.getId());
        // Disconnect from session so that the updates on updatedHopDosyaBorcKalem are not directly saved in db
        em.detach(updatedHopDosyaBorcKalem);
        updatedHopDosyaBorcKalem
            .kod(UPDATED_KOD)
            .tutar(UPDATED_TUTAR);

        restHopDosyaBorcKalemMockMvc.perform(put("/api/hop-dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHopDosyaBorcKalem)))
            .andExpect(status().isOk());

        // Validate the HopDosyaBorcKalem in the database
        List<HopDosyaBorcKalem> hopDosyaBorcKalemList = hopDosyaBorcKalemRepository.findAll();
        assertThat(hopDosyaBorcKalemList).hasSize(databaseSizeBeforeUpdate);
        HopDosyaBorcKalem testHopDosyaBorcKalem = hopDosyaBorcKalemList.get(hopDosyaBorcKalemList.size() - 1);
        assertThat(testHopDosyaBorcKalem.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testHopDosyaBorcKalem.getTutar()).isEqualTo(UPDATED_TUTAR);

        // Validate the HopDosyaBorcKalem in Elasticsearch
        HopDosyaBorcKalem hopDosyaBorcKalemEs = hopDosyaBorcKalemSearchRepository.findOne(testHopDosyaBorcKalem.getId());
        assertThat(hopDosyaBorcKalemEs).isEqualToIgnoringGivenFields(testHopDosyaBorcKalem);
    }

    @Test
    @Transactional
    public void updateNonExistingHopDosyaBorcKalem() throws Exception {
        int databaseSizeBeforeUpdate = hopDosyaBorcKalemRepository.findAll().size();

        // Create the HopDosyaBorcKalem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHopDosyaBorcKalemMockMvc.perform(put("/api/hop-dosya-borc-kalems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosyaBorcKalem)))
            .andExpect(status().isCreated());

        // Validate the HopDosyaBorcKalem in the database
        List<HopDosyaBorcKalem> hopDosyaBorcKalemList = hopDosyaBorcKalemRepository.findAll();
        assertThat(hopDosyaBorcKalemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHopDosyaBorcKalem() throws Exception {
        // Initialize the database
        hopDosyaBorcKalemService.save(hopDosyaBorcKalem);

        int databaseSizeBeforeDelete = hopDosyaBorcKalemRepository.findAll().size();

        // Get the hopDosyaBorcKalem
        restHopDosyaBorcKalemMockMvc.perform(delete("/api/hop-dosya-borc-kalems/{id}", hopDosyaBorcKalem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hopDosyaBorcKalemExistsInEs = hopDosyaBorcKalemSearchRepository.exists(hopDosyaBorcKalem.getId());
        assertThat(hopDosyaBorcKalemExistsInEs).isFalse();

        // Validate the database is empty
        List<HopDosyaBorcKalem> hopDosyaBorcKalemList = hopDosyaBorcKalemRepository.findAll();
        assertThat(hopDosyaBorcKalemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHopDosyaBorcKalem() throws Exception {
        // Initialize the database
        hopDosyaBorcKalemService.save(hopDosyaBorcKalem);

        // Search the hopDosyaBorcKalem
        restHopDosyaBorcKalemMockMvc.perform(get("/api/_search/hop-dosya-borc-kalems?query=id:" + hopDosyaBorcKalem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopDosyaBorcKalem.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopDosyaBorcKalem.class);
        HopDosyaBorcKalem hopDosyaBorcKalem1 = new HopDosyaBorcKalem();
        hopDosyaBorcKalem1.setId(1L);
        HopDosyaBorcKalem hopDosyaBorcKalem2 = new HopDosyaBorcKalem();
        hopDosyaBorcKalem2.setId(hopDosyaBorcKalem1.getId());
        assertThat(hopDosyaBorcKalem1).isEqualTo(hopDosyaBorcKalem2);
        hopDosyaBorcKalem2.setId(2L);
        assertThat(hopDosyaBorcKalem1).isNotEqualTo(hopDosyaBorcKalem2);
        hopDosyaBorcKalem1.setId(null);
        assertThat(hopDosyaBorcKalem1).isNotEqualTo(hopDosyaBorcKalem2);
    }
}
