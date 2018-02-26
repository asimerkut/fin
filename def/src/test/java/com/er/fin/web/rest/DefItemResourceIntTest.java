package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.DefItem;
import com.er.fin.domain.DefType;
import com.er.fin.repository.DefItemRepository;
import com.er.fin.service.DefItemService;
import com.er.fin.repository.search.DefItemSearchRepository;
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
 * Test class for the DefItemResource REST controller.
 *
 * @see DefItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DefItemResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ITEM_LEVEL = 9;
    private static final Integer UPDATED_ITEM_LEVEL = 8;

    private static final Boolean DEFAULT_IS_SELECT = false;
    private static final Boolean UPDATED_IS_SELECT = true;

    private static final Boolean DEFAULT_IS_CONST = false;
    private static final Boolean UPDATED_IS_CONST = true;

    @Autowired
    private DefItemRepository defItemRepository;

    @Autowired
    private DefItemService defItemService;

    @Autowired
    private DefItemSearchRepository defItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDefItemMockMvc;

    private DefItem defItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DefItemResource defItemResource = new DefItemResource(defItemService);
        this.restDefItemMockMvc = MockMvcBuilders.standaloneSetup(defItemResource)
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
    public static DefItem createEntity(EntityManager em) {
        DefItem defItem = new DefItem()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .itemLevel(DEFAULT_ITEM_LEVEL)
            .isSelect(DEFAULT_IS_SELECT)
            .isConst(DEFAULT_IS_CONST);
        // Add required entity
        DefType type = DefTypeResourceIntTest.createEntity(em);
        em.persist(type);
        em.flush();
        defItem.setType(type);
        return defItem;
    }

    @Before
    public void initTest() {
        defItemSearchRepository.deleteAll();
        defItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createDefItem() throws Exception {
        int databaseSizeBeforeCreate = defItemRepository.findAll().size();

        // Create the DefItem
        restDefItemMockMvc.perform(post("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isCreated());

        // Validate the DefItem in the database
        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeCreate + 1);
        DefItem testDefItem = defItemList.get(defItemList.size() - 1);
        assertThat(testDefItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDefItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDefItem.getItemLevel()).isEqualTo(DEFAULT_ITEM_LEVEL);
        assertThat(testDefItem.isIsSelect()).isEqualTo(DEFAULT_IS_SELECT);
        assertThat(testDefItem.isIsConst()).isEqualTo(DEFAULT_IS_CONST);

        // Validate the DefItem in Elasticsearch
        DefItem defItemEs = defItemSearchRepository.findOne(testDefItem.getId());
        assertThat(defItemEs).isEqualToIgnoringGivenFields(testDefItem);
    }

    @Test
    @Transactional
    public void createDefItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = defItemRepository.findAll().size();

        // Create the DefItem with an existing ID
        defItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefItemMockMvc.perform(post("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isBadRequest());

        // Validate the DefItem in the database
        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = defItemRepository.findAll().size();
        // set the field null
        defItem.setCode(null);

        // Create the DefItem, which fails.

        restDefItemMockMvc.perform(post("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isBadRequest());

        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = defItemRepository.findAll().size();
        // set the field null
        defItem.setName(null);

        // Create the DefItem, which fails.

        restDefItemMockMvc.perform(post("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isBadRequest());

        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = defItemRepository.findAll().size();
        // set the field null
        defItem.setItemLevel(null);

        // Create the DefItem, which fails.

        restDefItemMockMvc.perform(post("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isBadRequest());

        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSelectIsRequired() throws Exception {
        int databaseSizeBeforeTest = defItemRepository.findAll().size();
        // set the field null
        defItem.setIsSelect(null);

        // Create the DefItem, which fails.

        restDefItemMockMvc.perform(post("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isBadRequest());

        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsConstIsRequired() throws Exception {
        int databaseSizeBeforeTest = defItemRepository.findAll().size();
        // set the field null
        defItem.setIsConst(null);

        // Create the DefItem, which fails.

        restDefItemMockMvc.perform(post("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isBadRequest());

        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDefItems() throws Exception {
        // Initialize the database
        defItemRepository.saveAndFlush(defItem);

        // Get all the defItemList
        restDefItemMockMvc.perform(get("/api/def-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemLevel").value(hasItem(DEFAULT_ITEM_LEVEL)))
            .andExpect(jsonPath("$.[*].isSelect").value(hasItem(DEFAULT_IS_SELECT.booleanValue())))
            .andExpect(jsonPath("$.[*].isConst").value(hasItem(DEFAULT_IS_CONST.booleanValue())));
    }

    @Test
    @Transactional
    public void getDefItem() throws Exception {
        // Initialize the database
        defItemRepository.saveAndFlush(defItem);

        // Get the defItem
        restDefItemMockMvc.perform(get("/api/def-items/{id}", defItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(defItem.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.itemLevel").value(DEFAULT_ITEM_LEVEL))
            .andExpect(jsonPath("$.isSelect").value(DEFAULT_IS_SELECT.booleanValue()))
            .andExpect(jsonPath("$.isConst").value(DEFAULT_IS_CONST.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDefItem() throws Exception {
        // Get the defItem
        restDefItemMockMvc.perform(get("/api/def-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefItem() throws Exception {
        // Initialize the database
        defItemService.save(defItem);

        int databaseSizeBeforeUpdate = defItemRepository.findAll().size();

        // Update the defItem
        DefItem updatedDefItem = defItemRepository.findOne(defItem.getId());
        // Disconnect from session so that the updates on updatedDefItem are not directly saved in db
        em.detach(updatedDefItem);
        updatedDefItem
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .itemLevel(UPDATED_ITEM_LEVEL)
            .isSelect(UPDATED_IS_SELECT)
            .isConst(UPDATED_IS_CONST);

        restDefItemMockMvc.perform(put("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDefItem)))
            .andExpect(status().isOk());

        // Validate the DefItem in the database
        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeUpdate);
        DefItem testDefItem = defItemList.get(defItemList.size() - 1);
        assertThat(testDefItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDefItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDefItem.getItemLevel()).isEqualTo(UPDATED_ITEM_LEVEL);
        assertThat(testDefItem.isIsSelect()).isEqualTo(UPDATED_IS_SELECT);
        assertThat(testDefItem.isIsConst()).isEqualTo(UPDATED_IS_CONST);

        // Validate the DefItem in Elasticsearch
        DefItem defItemEs = defItemSearchRepository.findOne(testDefItem.getId());
        assertThat(defItemEs).isEqualToIgnoringGivenFields(testDefItem);
    }

    @Test
    @Transactional
    public void updateNonExistingDefItem() throws Exception {
        int databaseSizeBeforeUpdate = defItemRepository.findAll().size();

        // Create the DefItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDefItemMockMvc.perform(put("/api/def-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defItem)))
            .andExpect(status().isCreated());

        // Validate the DefItem in the database
        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDefItem() throws Exception {
        // Initialize the database
        defItemService.save(defItem);

        int databaseSizeBeforeDelete = defItemRepository.findAll().size();

        // Get the defItem
        restDefItemMockMvc.perform(delete("/api/def-items/{id}", defItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean defItemExistsInEs = defItemSearchRepository.exists(defItem.getId());
        assertThat(defItemExistsInEs).isFalse();

        // Validate the database is empty
        List<DefItem> defItemList = defItemRepository.findAll();
        assertThat(defItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDefItem() throws Exception {
        // Initialize the database
        defItemService.save(defItem);

        // Search the defItem
        restDefItemMockMvc.perform(get("/api/_search/def-items?query=id:" + defItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemLevel").value(hasItem(DEFAULT_ITEM_LEVEL)))
            .andExpect(jsonPath("$.[*].isSelect").value(hasItem(DEFAULT_IS_SELECT.booleanValue())))
            .andExpect(jsonPath("$.[*].isConst").value(hasItem(DEFAULT_IS_CONST.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefItem.class);
        DefItem defItem1 = new DefItem();
        defItem1.setId(1L);
        DefItem defItem2 = new DefItem();
        defItem2.setId(defItem1.getId());
        assertThat(defItem1).isEqualTo(defItem2);
        defItem2.setId(2L);
        assertThat(defItem1).isNotEqualTo(defItem2);
        defItem1.setId(null);
        assertThat(defItem1).isNotEqualTo(defItem2);
    }
}
