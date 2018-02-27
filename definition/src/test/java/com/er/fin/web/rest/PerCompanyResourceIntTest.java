package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.PerCompany;
import com.er.fin.repository.PerCompanyRepository;
import com.er.fin.service.PerCompanyService;
import com.er.fin.repository.search.PerCompanySearchRepository;
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
 * Test class for the PerCompanyResource REST controller.
 *
 * @see PerCompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class PerCompanyResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MESAI_OO = 8;
    private static final Integer UPDATED_MESAI_OO = 7;

    private static final Integer DEFAULT_MESAI_OS = 8;
    private static final Integer UPDATED_MESAI_OS = 7;

    private static final Integer DEFAULT_MESAI_GC = 8;
    private static final Integer UPDATED_MESAI_GC = 7;

    @Autowired
    private PerCompanyRepository perCompanyRepository;

    @Autowired
    private PerCompanyService perCompanyService;

    @Autowired
    private PerCompanySearchRepository perCompanySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerCompanyMockMvc;

    private PerCompany perCompany;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerCompanyResource perCompanyResource = new PerCompanyResource(perCompanyService);
        this.restPerCompanyMockMvc = MockMvcBuilders.standaloneSetup(perCompanyResource)
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
    public static PerCompany createEntity(EntityManager em) {
        PerCompany perCompany = new PerCompany()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .mesaiOo(DEFAULT_MESAI_OO)
            .mesaiOs(DEFAULT_MESAI_OS)
            .mesaiGc(DEFAULT_MESAI_GC);
        return perCompany;
    }

    @Before
    public void initTest() {
        perCompanySearchRepository.deleteAll();
        perCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerCompany() throws Exception {
        int databaseSizeBeforeCreate = perCompanyRepository.findAll().size();

        // Create the PerCompany
        restPerCompanyMockMvc.perform(post("/api/per-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perCompany)))
            .andExpect(status().isCreated());

        // Validate the PerCompany in the database
        List<PerCompany> perCompanyList = perCompanyRepository.findAll();
        assertThat(perCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        PerCompany testPerCompany = perCompanyList.get(perCompanyList.size() - 1);
        assertThat(testPerCompany.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPerCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerCompany.getMesaiOo()).isEqualTo(DEFAULT_MESAI_OO);
        assertThat(testPerCompany.getMesaiOs()).isEqualTo(DEFAULT_MESAI_OS);
        assertThat(testPerCompany.getMesaiGc()).isEqualTo(DEFAULT_MESAI_GC);

        // Validate the PerCompany in Elasticsearch
        PerCompany perCompanyEs = perCompanySearchRepository.findOne(testPerCompany.getId());
        assertThat(perCompanyEs).isEqualToIgnoringGivenFields(testPerCompany);
    }

    @Test
    @Transactional
    public void createPerCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perCompanyRepository.findAll().size();

        // Create the PerCompany with an existing ID
        perCompany.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerCompanyMockMvc.perform(post("/api/per-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perCompany)))
            .andExpect(status().isBadRequest());

        // Validate the PerCompany in the database
        List<PerCompany> perCompanyList = perCompanyRepository.findAll();
        assertThat(perCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = perCompanyRepository.findAll().size();
        // set the field null
        perCompany.setCode(null);

        // Create the PerCompany, which fails.

        restPerCompanyMockMvc.perform(post("/api/per-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perCompany)))
            .andExpect(status().isBadRequest());

        List<PerCompany> perCompanyList = perCompanyRepository.findAll();
        assertThat(perCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = perCompanyRepository.findAll().size();
        // set the field null
        perCompany.setName(null);

        // Create the PerCompany, which fails.

        restPerCompanyMockMvc.perform(post("/api/per-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perCompany)))
            .andExpect(status().isBadRequest());

        List<PerCompany> perCompanyList = perCompanyRepository.findAll();
        assertThat(perCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerCompanies() throws Exception {
        // Initialize the database
        perCompanyRepository.saveAndFlush(perCompany);

        // Get all the perCompanyList
        restPerCompanyMockMvc.perform(get("/api/per-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mesaiOo").value(hasItem(DEFAULT_MESAI_OO)))
            .andExpect(jsonPath("$.[*].mesaiOs").value(hasItem(DEFAULT_MESAI_OS)))
            .andExpect(jsonPath("$.[*].mesaiGc").value(hasItem(DEFAULT_MESAI_GC)));
    }

    @Test
    @Transactional
    public void getPerCompany() throws Exception {
        // Initialize the database
        perCompanyRepository.saveAndFlush(perCompany);

        // Get the perCompany
        restPerCompanyMockMvc.perform(get("/api/per-companies/{id}", perCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perCompany.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mesaiOo").value(DEFAULT_MESAI_OO))
            .andExpect(jsonPath("$.mesaiOs").value(DEFAULT_MESAI_OS))
            .andExpect(jsonPath("$.mesaiGc").value(DEFAULT_MESAI_GC));
    }

    @Test
    @Transactional
    public void getNonExistingPerCompany() throws Exception {
        // Get the perCompany
        restPerCompanyMockMvc.perform(get("/api/per-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerCompany() throws Exception {
        // Initialize the database
        perCompanyService.save(perCompany);

        int databaseSizeBeforeUpdate = perCompanyRepository.findAll().size();

        // Update the perCompany
        PerCompany updatedPerCompany = perCompanyRepository.findOne(perCompany.getId());
        // Disconnect from session so that the updates on updatedPerCompany are not directly saved in db
        em.detach(updatedPerCompany);
        updatedPerCompany
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .mesaiOo(UPDATED_MESAI_OO)
            .mesaiOs(UPDATED_MESAI_OS)
            .mesaiGc(UPDATED_MESAI_GC);

        restPerCompanyMockMvc.perform(put("/api/per-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerCompany)))
            .andExpect(status().isOk());

        // Validate the PerCompany in the database
        List<PerCompany> perCompanyList = perCompanyRepository.findAll();
        assertThat(perCompanyList).hasSize(databaseSizeBeforeUpdate);
        PerCompany testPerCompany = perCompanyList.get(perCompanyList.size() - 1);
        assertThat(testPerCompany.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPerCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerCompany.getMesaiOo()).isEqualTo(UPDATED_MESAI_OO);
        assertThat(testPerCompany.getMesaiOs()).isEqualTo(UPDATED_MESAI_OS);
        assertThat(testPerCompany.getMesaiGc()).isEqualTo(UPDATED_MESAI_GC);

        // Validate the PerCompany in Elasticsearch
        PerCompany perCompanyEs = perCompanySearchRepository.findOne(testPerCompany.getId());
        assertThat(perCompanyEs).isEqualToIgnoringGivenFields(testPerCompany);
    }

    @Test
    @Transactional
    public void updateNonExistingPerCompany() throws Exception {
        int databaseSizeBeforeUpdate = perCompanyRepository.findAll().size();

        // Create the PerCompany

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerCompanyMockMvc.perform(put("/api/per-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perCompany)))
            .andExpect(status().isCreated());

        // Validate the PerCompany in the database
        List<PerCompany> perCompanyList = perCompanyRepository.findAll();
        assertThat(perCompanyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerCompany() throws Exception {
        // Initialize the database
        perCompanyService.save(perCompany);

        int databaseSizeBeforeDelete = perCompanyRepository.findAll().size();

        // Get the perCompany
        restPerCompanyMockMvc.perform(delete("/api/per-companies/{id}", perCompany.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean perCompanyExistsInEs = perCompanySearchRepository.exists(perCompany.getId());
        assertThat(perCompanyExistsInEs).isFalse();

        // Validate the database is empty
        List<PerCompany> perCompanyList = perCompanyRepository.findAll();
        assertThat(perCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerCompany() throws Exception {
        // Initialize the database
        perCompanyService.save(perCompany);

        // Search the perCompany
        restPerCompanyMockMvc.perform(get("/api/_search/per-companies?query=id:" + perCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mesaiOo").value(hasItem(DEFAULT_MESAI_OO)))
            .andExpect(jsonPath("$.[*].mesaiOs").value(hasItem(DEFAULT_MESAI_OS)))
            .andExpect(jsonPath("$.[*].mesaiGc").value(hasItem(DEFAULT_MESAI_GC)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerCompany.class);
        PerCompany perCompany1 = new PerCompany();
        perCompany1.setId(1L);
        PerCompany perCompany2 = new PerCompany();
        perCompany2.setId(perCompany1.getId());
        assertThat(perCompany1).isEqualTo(perCompany2);
        perCompany2.setId(2L);
        assertThat(perCompany1).isNotEqualTo(perCompany2);
        perCompany1.setId(null);
        assertThat(perCompany1).isNotEqualTo(perCompany2);
    }
}
