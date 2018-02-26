package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.DefType;
import com.er.fin.repository.DefTypeRepository;
import com.er.fin.service.DefTypeService;
import com.er.fin.repository.search.DefTypeSearchRepository;
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

import com.er.fin.domain.enumeration.EnmType;
/**
 * Test class for the DefTypeResource REST controller.
 *
 * @see DefTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DefTypeResourceIntTest {

    private static final EnmType DEFAULT_CODE = EnmType.BANKA;
    private static final EnmType UPDATED_CODE = EnmType.BRANS;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DefTypeRepository defTypeRepository;

    @Autowired
    private DefTypeService defTypeService;

    @Autowired
    private DefTypeSearchRepository defTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDefTypeMockMvc;

    private DefType defType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DefTypeResource defTypeResource = new DefTypeResource(defTypeService);
        this.restDefTypeMockMvc = MockMvcBuilders.standaloneSetup(defTypeResource)
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
    public static DefType createEntity(EntityManager em) {
        DefType defType = new DefType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return defType;
    }

    @Before
    public void initTest() {
        defTypeSearchRepository.deleteAll();
        defType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDefType() throws Exception {
        int databaseSizeBeforeCreate = defTypeRepository.findAll().size();

        // Create the DefType
        restDefTypeMockMvc.perform(post("/api/def-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defType)))
            .andExpect(status().isCreated());

        // Validate the DefType in the database
        List<DefType> defTypeList = defTypeRepository.findAll();
        assertThat(defTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DefType testDefType = defTypeList.get(defTypeList.size() - 1);
        assertThat(testDefType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDefType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the DefType in Elasticsearch
        DefType defTypeEs = defTypeSearchRepository.findOne(testDefType.getId());
        assertThat(defTypeEs).isEqualToIgnoringGivenFields(testDefType);
    }

    @Test
    @Transactional
    public void createDefTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = defTypeRepository.findAll().size();

        // Create the DefType with an existing ID
        defType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefTypeMockMvc.perform(post("/api/def-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defType)))
            .andExpect(status().isBadRequest());

        // Validate the DefType in the database
        List<DefType> defTypeList = defTypeRepository.findAll();
        assertThat(defTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = defTypeRepository.findAll().size();
        // set the field null
        defType.setCode(null);

        // Create the DefType, which fails.

        restDefTypeMockMvc.perform(post("/api/def-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defType)))
            .andExpect(status().isBadRequest());

        List<DefType> defTypeList = defTypeRepository.findAll();
        assertThat(defTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = defTypeRepository.findAll().size();
        // set the field null
        defType.setName(null);

        // Create the DefType, which fails.

        restDefTypeMockMvc.perform(post("/api/def-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defType)))
            .andExpect(status().isBadRequest());

        List<DefType> defTypeList = defTypeRepository.findAll();
        assertThat(defTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDefTypes() throws Exception {
        // Initialize the database
        defTypeRepository.saveAndFlush(defType);

        // Get all the defTypeList
        restDefTypeMockMvc.perform(get("/api/def-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDefType() throws Exception {
        // Initialize the database
        defTypeRepository.saveAndFlush(defType);

        // Get the defType
        restDefTypeMockMvc.perform(get("/api/def-types/{id}", defType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(defType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDefType() throws Exception {
        // Get the defType
        restDefTypeMockMvc.perform(get("/api/def-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefType() throws Exception {
        // Initialize the database
        defTypeService.save(defType);

        int databaseSizeBeforeUpdate = defTypeRepository.findAll().size();

        // Update the defType
        DefType updatedDefType = defTypeRepository.findOne(defType.getId());
        // Disconnect from session so that the updates on updatedDefType are not directly saved in db
        em.detach(updatedDefType);
        updatedDefType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restDefTypeMockMvc.perform(put("/api/def-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDefType)))
            .andExpect(status().isOk());

        // Validate the DefType in the database
        List<DefType> defTypeList = defTypeRepository.findAll();
        assertThat(defTypeList).hasSize(databaseSizeBeforeUpdate);
        DefType testDefType = defTypeList.get(defTypeList.size() - 1);
        assertThat(testDefType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDefType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the DefType in Elasticsearch
        DefType defTypeEs = defTypeSearchRepository.findOne(testDefType.getId());
        assertThat(defTypeEs).isEqualToIgnoringGivenFields(testDefType);
    }

    @Test
    @Transactional
    public void updateNonExistingDefType() throws Exception {
        int databaseSizeBeforeUpdate = defTypeRepository.findAll().size();

        // Create the DefType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDefTypeMockMvc.perform(put("/api/def-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defType)))
            .andExpect(status().isCreated());

        // Validate the DefType in the database
        List<DefType> defTypeList = defTypeRepository.findAll();
        assertThat(defTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDefType() throws Exception {
        // Initialize the database
        defTypeService.save(defType);

        int databaseSizeBeforeDelete = defTypeRepository.findAll().size();

        // Get the defType
        restDefTypeMockMvc.perform(delete("/api/def-types/{id}", defType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean defTypeExistsInEs = defTypeSearchRepository.exists(defType.getId());
        assertThat(defTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<DefType> defTypeList = defTypeRepository.findAll();
        assertThat(defTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDefType() throws Exception {
        // Initialize the database
        defTypeService.save(defType);

        // Search the defType
        restDefTypeMockMvc.perform(get("/api/_search/def-types?query=id:" + defType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefType.class);
        DefType defType1 = new DefType();
        defType1.setId(1L);
        DefType defType2 = new DefType();
        defType2.setId(defType1.getId());
        assertThat(defType1).isEqualTo(defType2);
        defType2.setId(2L);
        assertThat(defType1).isNotEqualTo(defType2);
        defType1.setId(null);
        assertThat(defType1).isNotEqualTo(defType2);
    }
}
