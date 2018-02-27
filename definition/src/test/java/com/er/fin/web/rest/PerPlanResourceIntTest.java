package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.PerPlan;
import com.er.fin.domain.PerPerson;
import com.er.fin.domain.DefItem;
import com.er.fin.repository.PerPlanRepository;
import com.er.fin.service.PerPlanService;
import com.er.fin.repository.search.PerPlanSearchRepository;
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

import com.er.fin.domain.enumeration.EnmDay;
import com.er.fin.domain.enumeration.EnmDersGrup;
/**
 * Test class for the PerPlanResource REST controller.
 *
 * @see PerPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class PerPlanResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final EnmDay DEFAULT_DAY_NO = EnmDay.D1;
    private static final EnmDay UPDATED_DAY_NO = EnmDay.D2;

    private static final EnmDersGrup DEFAULT_DERS_GRUP = EnmDersGrup.D_GS;
    private static final EnmDersGrup UPDATED_DERS_GRUP = EnmDersGrup.GG;

    private static final Integer DEFAULT_DERS_NO = 15;
    private static final Integer UPDATED_DERS_NO = 14;

    private static final Integer DEFAULT_DERS_ADET = 15;
    private static final Integer UPDATED_DERS_ADET = 14;

    @Autowired
    private PerPlanRepository perPlanRepository;

    @Autowired
    private PerPlanService perPlanService;

    @Autowired
    private PerPlanSearchRepository perPlanSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerPlanMockMvc;

    private PerPlan perPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerPlanResource perPlanResource = new PerPlanResource(perPlanService);
        this.restPerPlanMockMvc = MockMvcBuilders.standaloneSetup(perPlanResource)
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
    public static PerPlan createEntity(EntityManager em) {
        PerPlan perPlan = new PerPlan()
            .startDate(DEFAULT_START_DATE)
            .dayNo(DEFAULT_DAY_NO)
            .dersGrup(DEFAULT_DERS_GRUP)
            .dersNo(DEFAULT_DERS_NO)
            .dersAdet(DEFAULT_DERS_ADET);
        // Add required entity
        PerPerson person = PerPersonResourceIntTest.createEntity(em);
        em.persist(person);
        em.flush();
        perPlan.setPerson(person);
        // Add required entity
        DefItem ders = DefItemResourceIntTest.createEntity(em);
        em.persist(ders);
        em.flush();
        perPlan.setDers(ders);
        return perPlan;
    }

    @Before
    public void initTest() {
        perPlanSearchRepository.deleteAll();
        perPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerPlan() throws Exception {
        int databaseSizeBeforeCreate = perPlanRepository.findAll().size();

        // Create the PerPlan
        restPerPlanMockMvc.perform(post("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isCreated());

        // Validate the PerPlan in the database
        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeCreate + 1);
        PerPlan testPerPlan = perPlanList.get(perPlanList.size() - 1);
        assertThat(testPerPlan.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPerPlan.getDayNo()).isEqualTo(DEFAULT_DAY_NO);
        assertThat(testPerPlan.getDersGrup()).isEqualTo(DEFAULT_DERS_GRUP);
        assertThat(testPerPlan.getDersNo()).isEqualTo(DEFAULT_DERS_NO);
        assertThat(testPerPlan.getDersAdet()).isEqualTo(DEFAULT_DERS_ADET);

        // Validate the PerPlan in Elasticsearch
        PerPlan perPlanEs = perPlanSearchRepository.findOne(testPerPlan.getId());
        assertThat(perPlanEs).isEqualToIgnoringGivenFields(testPerPlan);
    }

    @Test
    @Transactional
    public void createPerPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perPlanRepository.findAll().size();

        // Create the PerPlan with an existing ID
        perPlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerPlanMockMvc.perform(post("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isBadRequest());

        // Validate the PerPlan in the database
        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPlanRepository.findAll().size();
        // set the field null
        perPlan.setStartDate(null);

        // Create the PerPlan, which fails.

        restPerPlanMockMvc.perform(post("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isBadRequest());

        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDayNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPlanRepository.findAll().size();
        // set the field null
        perPlan.setDayNo(null);

        // Create the PerPlan, which fails.

        restPerPlanMockMvc.perform(post("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isBadRequest());

        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDersGrupIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPlanRepository.findAll().size();
        // set the field null
        perPlan.setDersGrup(null);

        // Create the PerPlan, which fails.

        restPerPlanMockMvc.perform(post("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isBadRequest());

        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDersNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPlanRepository.findAll().size();
        // set the field null
        perPlan.setDersNo(null);

        // Create the PerPlan, which fails.

        restPerPlanMockMvc.perform(post("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isBadRequest());

        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDersAdetIsRequired() throws Exception {
        int databaseSizeBeforeTest = perPlanRepository.findAll().size();
        // set the field null
        perPlan.setDersAdet(null);

        // Create the PerPlan, which fails.

        restPerPlanMockMvc.perform(post("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isBadRequest());

        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerPlans() throws Exception {
        // Initialize the database
        perPlanRepository.saveAndFlush(perPlan);

        // Get all the perPlanList
        restPerPlanMockMvc.perform(get("/api/per-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].dayNo").value(hasItem(DEFAULT_DAY_NO.toString())))
            .andExpect(jsonPath("$.[*].dersGrup").value(hasItem(DEFAULT_DERS_GRUP.toString())))
            .andExpect(jsonPath("$.[*].dersNo").value(hasItem(DEFAULT_DERS_NO)))
            .andExpect(jsonPath("$.[*].dersAdet").value(hasItem(DEFAULT_DERS_ADET)));
    }

    @Test
    @Transactional
    public void getPerPlan() throws Exception {
        // Initialize the database
        perPlanRepository.saveAndFlush(perPlan);

        // Get the perPlan
        restPerPlanMockMvc.perform(get("/api/per-plans/{id}", perPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perPlan.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.dayNo").value(DEFAULT_DAY_NO.toString()))
            .andExpect(jsonPath("$.dersGrup").value(DEFAULT_DERS_GRUP.toString()))
            .andExpect(jsonPath("$.dersNo").value(DEFAULT_DERS_NO))
            .andExpect(jsonPath("$.dersAdet").value(DEFAULT_DERS_ADET));
    }

    @Test
    @Transactional
    public void getNonExistingPerPlan() throws Exception {
        // Get the perPlan
        restPerPlanMockMvc.perform(get("/api/per-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerPlan() throws Exception {
        // Initialize the database
        perPlanService.save(perPlan);

        int databaseSizeBeforeUpdate = perPlanRepository.findAll().size();

        // Update the perPlan
        PerPlan updatedPerPlan = perPlanRepository.findOne(perPlan.getId());
        // Disconnect from session so that the updates on updatedPerPlan are not directly saved in db
        em.detach(updatedPerPlan);
        updatedPerPlan
            .startDate(UPDATED_START_DATE)
            .dayNo(UPDATED_DAY_NO)
            .dersGrup(UPDATED_DERS_GRUP)
            .dersNo(UPDATED_DERS_NO)
            .dersAdet(UPDATED_DERS_ADET);

        restPerPlanMockMvc.perform(put("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerPlan)))
            .andExpect(status().isOk());

        // Validate the PerPlan in the database
        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeUpdate);
        PerPlan testPerPlan = perPlanList.get(perPlanList.size() - 1);
        assertThat(testPerPlan.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPerPlan.getDayNo()).isEqualTo(UPDATED_DAY_NO);
        assertThat(testPerPlan.getDersGrup()).isEqualTo(UPDATED_DERS_GRUP);
        assertThat(testPerPlan.getDersNo()).isEqualTo(UPDATED_DERS_NO);
        assertThat(testPerPlan.getDersAdet()).isEqualTo(UPDATED_DERS_ADET);

        // Validate the PerPlan in Elasticsearch
        PerPlan perPlanEs = perPlanSearchRepository.findOne(testPerPlan.getId());
        assertThat(perPlanEs).isEqualToIgnoringGivenFields(testPerPlan);
    }

    @Test
    @Transactional
    public void updateNonExistingPerPlan() throws Exception {
        int databaseSizeBeforeUpdate = perPlanRepository.findAll().size();

        // Create the PerPlan

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerPlanMockMvc.perform(put("/api/per-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perPlan)))
            .andExpect(status().isCreated());

        // Validate the PerPlan in the database
        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerPlan() throws Exception {
        // Initialize the database
        perPlanService.save(perPlan);

        int databaseSizeBeforeDelete = perPlanRepository.findAll().size();

        // Get the perPlan
        restPerPlanMockMvc.perform(delete("/api/per-plans/{id}", perPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean perPlanExistsInEs = perPlanSearchRepository.exists(perPlan.getId());
        assertThat(perPlanExistsInEs).isFalse();

        // Validate the database is empty
        List<PerPlan> perPlanList = perPlanRepository.findAll();
        assertThat(perPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerPlan() throws Exception {
        // Initialize the database
        perPlanService.save(perPlan);

        // Search the perPlan
        restPerPlanMockMvc.perform(get("/api/_search/per-plans?query=id:" + perPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].dayNo").value(hasItem(DEFAULT_DAY_NO.toString())))
            .andExpect(jsonPath("$.[*].dersGrup").value(hasItem(DEFAULT_DERS_GRUP.toString())))
            .andExpect(jsonPath("$.[*].dersNo").value(hasItem(DEFAULT_DERS_NO)))
            .andExpect(jsonPath("$.[*].dersAdet").value(hasItem(DEFAULT_DERS_ADET)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerPlan.class);
        PerPlan perPlan1 = new PerPlan();
        perPlan1.setId(1L);
        PerPlan perPlan2 = new PerPlan();
        perPlan2.setId(perPlan1.getId());
        assertThat(perPlan1).isEqualTo(perPlan2);
        perPlan2.setId(2L);
        assertThat(perPlan1).isNotEqualTo(perPlan2);
        perPlan1.setId(null);
        assertThat(perPlan1).isNotEqualTo(perPlan2);
    }
}
