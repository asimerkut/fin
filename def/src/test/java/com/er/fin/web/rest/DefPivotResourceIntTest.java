package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.DefPivot;
import com.er.fin.repository.DefPivotRepository;
import com.er.fin.service.DefPivotService;
import com.er.fin.repository.search.DefPivotSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.er.fin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DefPivotResource REST controller.
 *
 * @see DefPivotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DefPivotResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PVT_SQL = "AAAAAAAAAA";
    private static final String UPDATED_PVT_SQL = "BBBBBBBBBB";

    private static final String DEFAULT_PVT_VAL = "AAAAAAAAAA";
    private static final String UPDATED_PVT_VAL = "BBBBBBBBBB";

    private static final String DEFAULT_PVT_COL = "AAAAAAAAAA";
    private static final String UPDATED_PVT_COL = "BBBBBBBBBB";

    private static final String DEFAULT_PVT_ROW = "AAAAAAAAAA";
    private static final String UPDATED_PVT_ROW = "BBBBBBBBBB";

    @Autowired
    private DefPivotRepository defPivotRepository;

    @Autowired
    private DefPivotService defPivotService;

    @Autowired
    private DefPivotSearchRepository defPivotSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDefPivotMockMvc;

    private DefPivot defPivot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DefPivotResource defPivotResource = new DefPivotResource(defPivotService);
        this.restDefPivotMockMvc = MockMvcBuilders.standaloneSetup(defPivotResource)
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
    public static DefPivot createEntity(EntityManager em) {
        DefPivot defPivot = new DefPivot()
            .code(DEFAULT_CODE)
            .pvtSql(DEFAULT_PVT_SQL)
            .pvtVal(DEFAULT_PVT_VAL)
            .pvtCol(DEFAULT_PVT_COL)
            .pvtRow(DEFAULT_PVT_ROW);
        return defPivot;
    }

    @Before
    public void initTest() {
        defPivotSearchRepository.deleteAll();
        defPivot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDefPivot() throws Exception {
        int databaseSizeBeforeCreate = defPivotRepository.findAll().size();

        // Create the DefPivot
        restDefPivotMockMvc.perform(post("/api/def-pivots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defPivot)))
            .andExpect(status().isCreated());

        // Validate the DefPivot in the database
        List<DefPivot> defPivotList = defPivotRepository.findAll();
        assertThat(defPivotList).hasSize(databaseSizeBeforeCreate + 1);
        DefPivot testDefPivot = defPivotList.get(defPivotList.size() - 1);
        assertThat(testDefPivot.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDefPivot.getPvtSql()).isEqualTo(DEFAULT_PVT_SQL);
        assertThat(testDefPivot.getPvtVal()).isEqualTo(DEFAULT_PVT_VAL);
        assertThat(testDefPivot.getPvtCol()).isEqualTo(DEFAULT_PVT_COL);
        assertThat(testDefPivot.getPvtRow()).isEqualTo(DEFAULT_PVT_ROW);

        // Validate the DefPivot in Elasticsearch
        DefPivot defPivotEs = defPivotSearchRepository.findOne(testDefPivot.getId());
        assertThat(defPivotEs).isEqualToIgnoringGivenFields(testDefPivot);
    }

    @Test
    @Transactional
    public void createDefPivotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = defPivotRepository.findAll().size();

        // Create the DefPivot with an existing ID
        defPivot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefPivotMockMvc.perform(post("/api/def-pivots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defPivot)))
            .andExpect(status().isBadRequest());

        // Validate the DefPivot in the database
        List<DefPivot> defPivotList = defPivotRepository.findAll();
        assertThat(defPivotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDefPivots() throws Exception {
        // Initialize the database
        defPivotRepository.saveAndFlush(defPivot);

        // Get all the defPivotList
        restDefPivotMockMvc.perform(get("/api/def-pivots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defPivot.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].pvtSql").value(hasItem(DEFAULT_PVT_SQL.toString())))
            .andExpect(jsonPath("$.[*].pvtVal").value(hasItem(DEFAULT_PVT_VAL.toString())))
            .andExpect(jsonPath("$.[*].pvtCol").value(hasItem(DEFAULT_PVT_COL.toString())))
            .andExpect(jsonPath("$.[*].pvtRow").value(hasItem(DEFAULT_PVT_ROW.toString())));
    }

    @Test
    @Transactional
    public void getDefPivot() throws Exception {
        // Initialize the database
        defPivotRepository.saveAndFlush(defPivot);

        // Get the defPivot
        restDefPivotMockMvc.perform(get("/api/def-pivots/{id}", defPivot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(defPivot.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.pvtSql").value(DEFAULT_PVT_SQL.toString()))
            .andExpect(jsonPath("$.pvtVal").value(DEFAULT_PVT_VAL.toString()))
            .andExpect(jsonPath("$.pvtCol").value(DEFAULT_PVT_COL.toString()))
            .andExpect(jsonPath("$.pvtRow").value(DEFAULT_PVT_ROW.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDefPivot() throws Exception {
        // Get the defPivot
        restDefPivotMockMvc.perform(get("/api/def-pivots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefPivot() throws Exception {
        // Initialize the database
        defPivotService.save(defPivot);

        int databaseSizeBeforeUpdate = defPivotRepository.findAll().size();

        // Update the defPivot
        DefPivot updatedDefPivot = defPivotRepository.findOne(defPivot.getId());
        // Disconnect from session so that the updates on updatedDefPivot are not directly saved in db
        em.detach(updatedDefPivot);
        updatedDefPivot
            .code(UPDATED_CODE)
            .pvtSql(UPDATED_PVT_SQL)
            .pvtVal(UPDATED_PVT_VAL)
            .pvtCol(UPDATED_PVT_COL)
            .pvtRow(UPDATED_PVT_ROW);

        restDefPivotMockMvc.perform(put("/api/def-pivots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDefPivot)))
            .andExpect(status().isOk());

        // Validate the DefPivot in the database
        List<DefPivot> defPivotList = defPivotRepository.findAll();
        assertThat(defPivotList).hasSize(databaseSizeBeforeUpdate);
        DefPivot testDefPivot = defPivotList.get(defPivotList.size() - 1);
        assertThat(testDefPivot.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDefPivot.getPvtSql()).isEqualTo(UPDATED_PVT_SQL);
        assertThat(testDefPivot.getPvtVal()).isEqualTo(UPDATED_PVT_VAL);
        assertThat(testDefPivot.getPvtCol()).isEqualTo(UPDATED_PVT_COL);
        assertThat(testDefPivot.getPvtRow()).isEqualTo(UPDATED_PVT_ROW);

        // Validate the DefPivot in Elasticsearch
        DefPivot defPivotEs = defPivotSearchRepository.findOne(testDefPivot.getId());
        assertThat(defPivotEs).isEqualToIgnoringGivenFields(testDefPivot);
    }

    @Test
    @Transactional
    public void updateNonExistingDefPivot() throws Exception {
        int databaseSizeBeforeUpdate = defPivotRepository.findAll().size();

        // Create the DefPivot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDefPivotMockMvc.perform(put("/api/def-pivots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defPivot)))
            .andExpect(status().isCreated());

        // Validate the DefPivot in the database
        List<DefPivot> defPivotList = defPivotRepository.findAll();
        assertThat(defPivotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDefPivot() throws Exception {
        // Initialize the database
        defPivotService.save(defPivot);

        int databaseSizeBeforeDelete = defPivotRepository.findAll().size();

        // Get the defPivot
        restDefPivotMockMvc.perform(delete("/api/def-pivots/{id}", defPivot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean defPivotExistsInEs = defPivotSearchRepository.exists(defPivot.getId());
        assertThat(defPivotExistsInEs).isFalse();

        // Validate the database is empty
        List<DefPivot> defPivotList = defPivotRepository.findAll();
        assertThat(defPivotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDefPivot() throws Exception {
        // Initialize the database
        defPivotService.save(defPivot);

        // Search the defPivot
        restDefPivotMockMvc.perform(get("/api/_search/def-pivots?query=id:" + defPivot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defPivot.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].pvtSql").value(hasItem(DEFAULT_PVT_SQL.toString())))
            .andExpect(jsonPath("$.[*].pvtVal").value(hasItem(DEFAULT_PVT_VAL.toString())))
            .andExpect(jsonPath("$.[*].pvtCol").value(hasItem(DEFAULT_PVT_COL.toString())))
            .andExpect(jsonPath("$.[*].pvtRow").value(hasItem(DEFAULT_PVT_ROW.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefPivot.class);
        DefPivot defPivot1 = new DefPivot();
        defPivot1.setId(1L);
        DefPivot defPivot2 = new DefPivot();
        defPivot2.setId(defPivot1.getId());
        assertThat(defPivot1).isEqualTo(defPivot2);
        defPivot2.setId(2L);
        assertThat(defPivot1).isNotEqualTo(defPivot2);
        defPivot1.setId(null);
        assertThat(defPivot1).isNotEqualTo(defPivot2);
    }
}
