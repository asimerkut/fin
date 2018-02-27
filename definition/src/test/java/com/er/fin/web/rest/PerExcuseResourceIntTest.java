package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.PerExcuse;
import com.er.fin.domain.PerPerson;
import com.er.fin.repository.PerExcuseRepository;
import com.er.fin.service.PerExcuseService;
import com.er.fin.repository.search.PerExcuseSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.er.fin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PerExcuseResource REST controller.
 *
 * @see PerExcuseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class PerExcuseResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_START_DERS_NO = 15;
    private static final Integer UPDATED_START_DERS_NO = 14;

    private static final LocalDate DEFAULT_FINISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_FINISH_DERS_NO = 15;
    private static final Integer UPDATED_FINISH_DERS_NO = 14;

    private static final Boolean DEFAULT_IS_EXCUSE = false;
    private static final Boolean UPDATED_IS_EXCUSE = true;

    @Autowired
    private PerExcuseRepository perExcuseRepository;

    @Autowired
    private PerExcuseService perExcuseService;

    @Autowired
    private PerExcuseSearchRepository perExcuseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerExcuseMockMvc;

    private PerExcuse perExcuse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerExcuseResource perExcuseResource = new PerExcuseResource(perExcuseService);
        this.restPerExcuseMockMvc = MockMvcBuilders.standaloneSetup(perExcuseResource)
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
    public static PerExcuse createEntity(EntityManager em) {
        PerExcuse perExcuse = new PerExcuse()
            .startDate(DEFAULT_START_DATE)
            .startDersNo(DEFAULT_START_DERS_NO)
            .finishDate(DEFAULT_FINISH_DATE)
            .finishDersNo(DEFAULT_FINISH_DERS_NO)
            .isExcuse(DEFAULT_IS_EXCUSE);
        // Add required entity
        PerPerson person = PerPersonResourceIntTest.createEntity(em);
        em.persist(person);
        em.flush();
        perExcuse.setPerson(person);
        return perExcuse;
    }

    @Before
    public void initTest() {
        perExcuseSearchRepository.deleteAll();
        perExcuse = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerExcuse() throws Exception {
        int databaseSizeBeforeCreate = perExcuseRepository.findAll().size();

        // Create the PerExcuse
        restPerExcuseMockMvc.perform(post("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isCreated());

        // Validate the PerExcuse in the database
        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeCreate + 1);
        PerExcuse testPerExcuse = perExcuseList.get(perExcuseList.size() - 1);
        assertThat(testPerExcuse.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPerExcuse.getStartDersNo()).isEqualTo(DEFAULT_START_DERS_NO);
        assertThat(testPerExcuse.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testPerExcuse.getFinishDersNo()).isEqualTo(DEFAULT_FINISH_DERS_NO);
        assertThat(testPerExcuse.isIsExcuse()).isEqualTo(DEFAULT_IS_EXCUSE);

        // Validate the PerExcuse in Elasticsearch
        PerExcuse perExcuseEs = perExcuseSearchRepository.findOne(testPerExcuse.getId());
        assertThat(perExcuseEs).isEqualToIgnoringGivenFields(testPerExcuse);
    }

    @Test
    @Transactional
    public void createPerExcuseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perExcuseRepository.findAll().size();

        // Create the PerExcuse with an existing ID
        perExcuse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerExcuseMockMvc.perform(post("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isBadRequest());

        // Validate the PerExcuse in the database
        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = perExcuseRepository.findAll().size();
        // set the field null
        perExcuse.setStartDate(null);

        // Create the PerExcuse, which fails.

        restPerExcuseMockMvc.perform(post("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isBadRequest());

        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDersNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perExcuseRepository.findAll().size();
        // set the field null
        perExcuse.setStartDersNo(null);

        // Create the PerExcuse, which fails.

        restPerExcuseMockMvc.perform(post("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isBadRequest());

        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinishDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = perExcuseRepository.findAll().size();
        // set the field null
        perExcuse.setFinishDate(null);

        // Create the PerExcuse, which fails.

        restPerExcuseMockMvc.perform(post("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isBadRequest());

        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinishDersNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perExcuseRepository.findAll().size();
        // set the field null
        perExcuse.setFinishDersNo(null);

        // Create the PerExcuse, which fails.

        restPerExcuseMockMvc.perform(post("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isBadRequest());

        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsExcuseIsRequired() throws Exception {
        int databaseSizeBeforeTest = perExcuseRepository.findAll().size();
        // set the field null
        perExcuse.setIsExcuse(null);

        // Create the PerExcuse, which fails.

        restPerExcuseMockMvc.perform(post("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isBadRequest());

        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerExcuses() throws Exception {
        // Initialize the database
        perExcuseRepository.saveAndFlush(perExcuse);

        // Get all the perExcuseList
        restPerExcuseMockMvc.perform(get("/api/per-excuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perExcuse.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDersNo").value(hasItem(DEFAULT_START_DERS_NO)))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDersNo").value(hasItem(DEFAULT_FINISH_DERS_NO)))
            .andExpect(jsonPath("$.[*].isExcuse").value(hasItem(DEFAULT_IS_EXCUSE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPerExcuse() throws Exception {
        // Initialize the database
        perExcuseRepository.saveAndFlush(perExcuse);

        // Get the perExcuse
        restPerExcuseMockMvc.perform(get("/api/per-excuses/{id}", perExcuse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perExcuse.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.startDersNo").value(DEFAULT_START_DERS_NO))
            .andExpect(jsonPath("$.finishDate").value(DEFAULT_FINISH_DATE.toString()))
            .andExpect(jsonPath("$.finishDersNo").value(DEFAULT_FINISH_DERS_NO))
            .andExpect(jsonPath("$.isExcuse").value(DEFAULT_IS_EXCUSE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPerExcuse() throws Exception {
        // Get the perExcuse
        restPerExcuseMockMvc.perform(get("/api/per-excuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerExcuse() throws Exception {
        // Initialize the database
        perExcuseService.save(perExcuse);

        int databaseSizeBeforeUpdate = perExcuseRepository.findAll().size();

        // Update the perExcuse
        PerExcuse updatedPerExcuse = perExcuseRepository.findOne(perExcuse.getId());
        // Disconnect from session so that the updates on updatedPerExcuse are not directly saved in db
        em.detach(updatedPerExcuse);
        updatedPerExcuse
            .startDate(UPDATED_START_DATE)
            .startDersNo(UPDATED_START_DERS_NO)
            .finishDate(UPDATED_FINISH_DATE)
            .finishDersNo(UPDATED_FINISH_DERS_NO)
            .isExcuse(UPDATED_IS_EXCUSE);

        restPerExcuseMockMvc.perform(put("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerExcuse)))
            .andExpect(status().isOk());

        // Validate the PerExcuse in the database
        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeUpdate);
        PerExcuse testPerExcuse = perExcuseList.get(perExcuseList.size() - 1);
        assertThat(testPerExcuse.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPerExcuse.getStartDersNo()).isEqualTo(UPDATED_START_DERS_NO);
        assertThat(testPerExcuse.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testPerExcuse.getFinishDersNo()).isEqualTo(UPDATED_FINISH_DERS_NO);
        assertThat(testPerExcuse.isIsExcuse()).isEqualTo(UPDATED_IS_EXCUSE);

        // Validate the PerExcuse in Elasticsearch
        PerExcuse perExcuseEs = perExcuseSearchRepository.findOne(testPerExcuse.getId());
        assertThat(perExcuseEs).isEqualToIgnoringGivenFields(testPerExcuse);
    }

    @Test
    @Transactional
    public void updateNonExistingPerExcuse() throws Exception {
        int databaseSizeBeforeUpdate = perExcuseRepository.findAll().size();

        // Create the PerExcuse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerExcuseMockMvc.perform(put("/api/per-excuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perExcuse)))
            .andExpect(status().isCreated());

        // Validate the PerExcuse in the database
        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerExcuse() throws Exception {
        // Initialize the database
        perExcuseService.save(perExcuse);

        int databaseSizeBeforeDelete = perExcuseRepository.findAll().size();

        // Get the perExcuse
        restPerExcuseMockMvc.perform(delete("/api/per-excuses/{id}", perExcuse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean perExcuseExistsInEs = perExcuseSearchRepository.exists(perExcuse.getId());
        assertThat(perExcuseExistsInEs).isFalse();

        // Validate the database is empty
        List<PerExcuse> perExcuseList = perExcuseRepository.findAll();
        assertThat(perExcuseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerExcuse() throws Exception {
        // Initialize the database
        perExcuseService.save(perExcuse);

        // Search the perExcuse
        restPerExcuseMockMvc.perform(get("/api/_search/per-excuses?query=id:" + perExcuse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perExcuse.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDersNo").value(hasItem(DEFAULT_START_DERS_NO)))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDersNo").value(hasItem(DEFAULT_FINISH_DERS_NO)))
            .andExpect(jsonPath("$.[*].isExcuse").value(hasItem(DEFAULT_IS_EXCUSE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerExcuse.class);
        PerExcuse perExcuse1 = new PerExcuse();
        perExcuse1.setId(1L);
        PerExcuse perExcuse2 = new PerExcuse();
        perExcuse2.setId(perExcuse1.getId());
        assertThat(perExcuse1).isEqualTo(perExcuse2);
        perExcuse2.setId(2L);
        assertThat(perExcuse1).isNotEqualTo(perExcuse2);
        perExcuse1.setId(null);
        assertThat(perExcuse1).isNotEqualTo(perExcuse2);
    }
}
