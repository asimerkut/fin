package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.PerPerson;
import com.er.fin.repository.PerPersonRepository;
import com.er.fin.service.PerPersonService;
import com.er.fin.repository.search.PerPersonSearchRepository;
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

import com.er.fin.domain.enumeration.EnmCins;
import com.er.fin.domain.enumeration.EnmMedeni;
/**
 * Test class for the PerPersonResource REST controller.
 *
 * @see PerPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class PerPersonResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final EnmCins DEFAULT_CINS = EnmCins.E;
    private static final EnmCins UPDATED_CINS = EnmCins.K;

    private static final EnmMedeni DEFAULT_MEDENI = EnmMedeni.BEK;
    private static final EnmMedeni UPDATED_MEDENI = EnmMedeni.EVL;

    @Autowired
    private PerPersonRepository perPersonRepository;

    @Autowired
    private PerPersonService perPersonService;

    @Autowired
    private PerPersonSearchRepository perPersonSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerPersonMockMvc;

    private PerPerson perPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerPersonResource perPersonResource = new PerPersonResource(perPersonService);
        this.restPerPersonMockMvc = MockMvcBuilders.standaloneSetup(perPersonResource)
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
    public static PerPerson createEntity(EntityManager em) {
        PerPerson perPerson = new PerPerson()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .cins(DEFAULT_CINS)
            .medeni(DEFAULT_MEDENI);
        return perPerson;
    }

    @Before
    public void initTest() {
        perPersonSearchRepository.deleteAll();
        perPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerPerson() throws Exception {
        int databaseSizeBeforeCreate = perPersonRepository.findAll().size();

        // Create the PerPerson
        restPerPersonMockMvc.perform(post("/api/per-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPerson)))
            .andExpect(status().isCreated());

        // Validate the PerPerson in the database
        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeCreate + 1);
        PerPerson testPerPerson = perPersonList.get(perPersonList.size() - 1);
        assertThat(testPerPerson.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPerPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerPerson.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPerPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPerPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPerPerson.getCins()).isEqualTo(DEFAULT_CINS);
        assertThat(testPerPerson.getMedeni()).isEqualTo(DEFAULT_MEDENI);

        // Validate the PerPerson in Elasticsearch
        PerPerson perPersonEs = perPersonSearchRepository.findOne(testPerPerson.getId());
        assertThat(perPersonEs).isEqualToIgnoringGivenFields(testPerPerson);
    }

    @Test
    @Transactional
    public void createPerPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perPersonRepository.findAll().size();

        // Create the PerPerson with an existing ID
        perPerson.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerPersonMockMvc.perform(post("/api/per-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPerson)))
            .andExpect(status().isBadRequest());

        // Validate the PerPerson in the database
        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPersonRepository.findAll().size();
        // set the field null
        perPerson.setCode(null);

        // Create the PerPerson, which fails.

        restPerPersonMockMvc.perform(post("/api/per-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPerson)))
            .andExpect(status().isBadRequest());

        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPersonRepository.findAll().size();
        // set the field null
        perPerson.setName(null);

        // Create the PerPerson, which fails.

        restPerPersonMockMvc.perform(post("/api/per-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPerson)))
            .andExpect(status().isBadRequest());

        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPersonRepository.findAll().size();
        // set the field null
        perPerson.setIsActive(null);

        // Create the PerPerson, which fails.

        restPerPersonMockMvc.perform(post("/api/per-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPerson)))
            .andExpect(status().isBadRequest());

        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerPeople() throws Exception {
        // Initialize the database
        perPersonRepository.saveAndFlush(perPerson);

        // Get all the perPersonList
        restPerPersonMockMvc.perform(get("/api/per-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].cins").value(hasItem(DEFAULT_CINS.toString())))
            .andExpect(jsonPath("$.[*].medeni").value(hasItem(DEFAULT_MEDENI.toString())));
    }

    @Test
    @Transactional
    public void getPerPerson() throws Exception {
        // Initialize the database
        perPersonRepository.saveAndFlush(perPerson);

        // Get the perPerson
        restPerPersonMockMvc.perform(get("/api/per-people/{id}", perPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perPerson.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.cins").value(DEFAULT_CINS.toString()))
            .andExpect(jsonPath("$.medeni").value(DEFAULT_MEDENI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerPerson() throws Exception {
        // Get the perPerson
        restPerPersonMockMvc.perform(get("/api/per-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerPerson() throws Exception {
        // Initialize the database
        perPersonService.save(perPerson);

        int databaseSizeBeforeUpdate = perPersonRepository.findAll().size();

        // Update the perPerson
        PerPerson updatedPerPerson = perPersonRepository.findOne(perPerson.getId());
        // Disconnect from session so that the updates on updatedPerPerson are not directly saved in db
        em.detach(updatedPerPerson);
        updatedPerPerson
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .cins(UPDATED_CINS)
            .medeni(UPDATED_MEDENI);

        restPerPersonMockMvc.perform(put("/api/per-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerPerson)))
            .andExpect(status().isOk());

        // Validate the PerPerson in the database
        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeUpdate);
        PerPerson testPerPerson = perPersonList.get(perPersonList.size() - 1);
        assertThat(testPerPerson.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPerPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerPerson.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPerPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPerPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPerPerson.getCins()).isEqualTo(UPDATED_CINS);
        assertThat(testPerPerson.getMedeni()).isEqualTo(UPDATED_MEDENI);

        // Validate the PerPerson in Elasticsearch
        PerPerson perPersonEs = perPersonSearchRepository.findOne(testPerPerson.getId());
        assertThat(perPersonEs).isEqualToIgnoringGivenFields(testPerPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingPerPerson() throws Exception {
        int databaseSizeBeforeUpdate = perPersonRepository.findAll().size();

        // Create the PerPerson

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerPersonMockMvc.perform(put("/api/per-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPerson)))
            .andExpect(status().isCreated());

        // Validate the PerPerson in the database
        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerPerson() throws Exception {
        // Initialize the database
        perPersonService.save(perPerson);

        int databaseSizeBeforeDelete = perPersonRepository.findAll().size();

        // Get the perPerson
        restPerPersonMockMvc.perform(delete("/api/per-people/{id}", perPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean perPersonExistsInEs = perPersonSearchRepository.exists(perPerson.getId());
        assertThat(perPersonExistsInEs).isFalse();

        // Validate the database is empty
        List<PerPerson> perPersonList = perPersonRepository.findAll();
        assertThat(perPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerPerson() throws Exception {
        // Initialize the database
        perPersonService.save(perPerson);

        // Search the perPerson
        restPerPersonMockMvc.perform(get("/api/_search/per-people?query=id:" + perPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].cins").value(hasItem(DEFAULT_CINS.toString())))
            .andExpect(jsonPath("$.[*].medeni").value(hasItem(DEFAULT_MEDENI.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerPerson.class);
        PerPerson perPerson1 = new PerPerson();
        perPerson1.setId(1L);
        PerPerson perPerson2 = new PerPerson();
        perPerson2.setId(perPerson1.getId());
        assertThat(perPerson1).isEqualTo(perPerson2);
        perPerson2.setId(2L);
        assertThat(perPerson1).isNotEqualTo(perPerson2);
        perPerson1.setId(null);
        assertThat(perPerson1).isNotEqualTo(perPerson2);
    }
}
