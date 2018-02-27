package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.PerSubmit;
import com.er.fin.domain.PerPerson;
import com.er.fin.domain.DefItem;
import com.er.fin.repository.PerSubmitRepository;
import com.er.fin.service.PerSubmitService;
import com.er.fin.repository.search.PerSubmitSearchRepository;
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

import com.er.fin.domain.enumeration.EnmDersGrup;
/**
 * Test class for the PerSubmitResource REST controller.
 *
 * @see PerSubmitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class PerSubmitResourceIntTest {

    private static final LocalDate DEFAULT_SUBMIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBMIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final EnmDersGrup DEFAULT_DERS_GRUP = EnmDersGrup.D_GS;
    private static final EnmDersGrup UPDATED_DERS_GRUP = EnmDersGrup.GG;

    private static final Integer DEFAULT_DERS_SIRA = 15;
    private static final Integer UPDATED_DERS_SIRA = 14;

    private static final Integer DEFAULT_DERS_ADET = 15;
    private static final Integer UPDATED_DERS_ADET = 14;

    @Autowired
    private PerSubmitRepository perSubmitRepository;

    @Autowired
    private PerSubmitService perSubmitService;

    @Autowired
    private PerSubmitSearchRepository perSubmitSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerSubmitMockMvc;

    private PerSubmit perSubmit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerSubmitResource perSubmitResource = new PerSubmitResource(perSubmitService);
        this.restPerSubmitMockMvc = MockMvcBuilders.standaloneSetup(perSubmitResource)
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
    public static PerSubmit createEntity(EntityManager em) {
        PerSubmit perSubmit = new PerSubmit()
            .submitDate(DEFAULT_SUBMIT_DATE)
            .dersGrup(DEFAULT_DERS_GRUP)
            .dersSira(DEFAULT_DERS_SIRA)
            .dersAdet(DEFAULT_DERS_ADET);
        // Add required entity
        PerPerson person = PerPersonResourceIntTest.createEntity(em);
        em.persist(person);
        em.flush();
        perSubmit.setPerson(person);
        // Add required entity
        DefItem ders = DefItemResourceIntTest.createEntity(em);
        em.persist(ders);
        em.flush();
        perSubmit.setDers(ders);
        return perSubmit;
    }

    @Before
    public void initTest() {
        perSubmitSearchRepository.deleteAll();
        perSubmit = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerSubmit() throws Exception {
        int databaseSizeBeforeCreate = perSubmitRepository.findAll().size();

        // Create the PerSubmit
        restPerSubmitMockMvc.perform(post("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perSubmit)))
            .andExpect(status().isCreated());

        // Validate the PerSubmit in the database
        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeCreate + 1);
        PerSubmit testPerSubmit = perSubmitList.get(perSubmitList.size() - 1);
        assertThat(testPerSubmit.getSubmitDate()).isEqualTo(DEFAULT_SUBMIT_DATE);
        assertThat(testPerSubmit.getDersGrup()).isEqualTo(DEFAULT_DERS_GRUP);
        assertThat(testPerSubmit.getDersSira()).isEqualTo(DEFAULT_DERS_SIRA);
        assertThat(testPerSubmit.getDersAdet()).isEqualTo(DEFAULT_DERS_ADET);

        // Validate the PerSubmit in Elasticsearch
        PerSubmit perSubmitEs = perSubmitSearchRepository.findOne(testPerSubmit.getId());
        assertThat(perSubmitEs).isEqualToIgnoringGivenFields(testPerSubmit);
    }

    @Test
    @Transactional
    public void createPerSubmitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perSubmitRepository.findAll().size();

        // Create the PerSubmit with an existing ID
        perSubmit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerSubmitMockMvc.perform(post("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perSubmit)))
            .andExpect(status().isBadRequest());

        // Validate the PerSubmit in the database
        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSubmitDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = perSubmitRepository.findAll().size();
        // set the field null
        perSubmit.setSubmitDate(null);

        // Create the PerSubmit, which fails.

        restPerSubmitMockMvc.perform(post("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perSubmit)))
            .andExpect(status().isBadRequest());

        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDersGrupIsRequired() throws Exception {
        int databaseSizeBeforeTest = perSubmitRepository.findAll().size();
        // set the field null
        perSubmit.setDersGrup(null);

        // Create the PerSubmit, which fails.

        restPerSubmitMockMvc.perform(post("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perSubmit)))
            .andExpect(status().isBadRequest());

        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDersSiraIsRequired() throws Exception {
        int databaseSizeBeforeTest = perSubmitRepository.findAll().size();
        // set the field null
        perSubmit.setDersSira(null);

        // Create the PerSubmit, which fails.

        restPerSubmitMockMvc.perform(post("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perSubmit)))
            .andExpect(status().isBadRequest());

        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDersAdetIsRequired() throws Exception {
        int databaseSizeBeforeTest = perSubmitRepository.findAll().size();
        // set the field null
        perSubmit.setDersAdet(null);

        // Create the PerSubmit, which fails.

        restPerSubmitMockMvc.perform(post("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perSubmit)))
            .andExpect(status().isBadRequest());

        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerSubmits() throws Exception {
        // Initialize the database
        perSubmitRepository.saveAndFlush(perSubmit);

        // Get all the perSubmitList
        restPerSubmitMockMvc.perform(get("/api/per-submits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perSubmit.getId().intValue())))
            .andExpect(jsonPath("$.[*].submitDate").value(hasItem(DEFAULT_SUBMIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dersGrup").value(hasItem(DEFAULT_DERS_GRUP.toString())))
            .andExpect(jsonPath("$.[*].dersSira").value(hasItem(DEFAULT_DERS_SIRA)))
            .andExpect(jsonPath("$.[*].dersAdet").value(hasItem(DEFAULT_DERS_ADET)));
    }

    @Test
    @Transactional
    public void getPerSubmit() throws Exception {
        // Initialize the database
        perSubmitRepository.saveAndFlush(perSubmit);

        // Get the perSubmit
        restPerSubmitMockMvc.perform(get("/api/per-submits/{id}", perSubmit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perSubmit.getId().intValue()))
            .andExpect(jsonPath("$.submitDate").value(DEFAULT_SUBMIT_DATE.toString()))
            .andExpect(jsonPath("$.dersGrup").value(DEFAULT_DERS_GRUP.toString()))
            .andExpect(jsonPath("$.dersSira").value(DEFAULT_DERS_SIRA))
            .andExpect(jsonPath("$.dersAdet").value(DEFAULT_DERS_ADET));
    }

    @Test
    @Transactional
    public void getNonExistingPerSubmit() throws Exception {
        // Get the perSubmit
        restPerSubmitMockMvc.perform(get("/api/per-submits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerSubmit() throws Exception {
        // Initialize the database
        perSubmitService.save(perSubmit);

        int databaseSizeBeforeUpdate = perSubmitRepository.findAll().size();

        // Update the perSubmit
        PerSubmit updatedPerSubmit = perSubmitRepository.findOne(perSubmit.getId());
        // Disconnect from session so that the updates on updatedPerSubmit are not directly saved in db
        em.detach(updatedPerSubmit);
        updatedPerSubmit
            .submitDate(UPDATED_SUBMIT_DATE)
            .dersGrup(UPDATED_DERS_GRUP)
            .dersSira(UPDATED_DERS_SIRA)
            .dersAdet(UPDATED_DERS_ADET);

        restPerSubmitMockMvc.perform(put("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerSubmit)))
            .andExpect(status().isOk());

        // Validate the PerSubmit in the database
        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeUpdate);
        PerSubmit testPerSubmit = perSubmitList.get(perSubmitList.size() - 1);
        assertThat(testPerSubmit.getSubmitDate()).isEqualTo(UPDATED_SUBMIT_DATE);
        assertThat(testPerSubmit.getDersGrup()).isEqualTo(UPDATED_DERS_GRUP);
        assertThat(testPerSubmit.getDersSira()).isEqualTo(UPDATED_DERS_SIRA);
        assertThat(testPerSubmit.getDersAdet()).isEqualTo(UPDATED_DERS_ADET);

        // Validate the PerSubmit in Elasticsearch
        PerSubmit perSubmitEs = perSubmitSearchRepository.findOne(testPerSubmit.getId());
        assertThat(perSubmitEs).isEqualToIgnoringGivenFields(testPerSubmit);
    }

    @Test
    @Transactional
    public void updateNonExistingPerSubmit() throws Exception {
        int databaseSizeBeforeUpdate = perSubmitRepository.findAll().size();

        // Create the PerSubmit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerSubmitMockMvc.perform(put("/api/per-submits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perSubmit)))
            .andExpect(status().isCreated());

        // Validate the PerSubmit in the database
        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerSubmit() throws Exception {
        // Initialize the database
        perSubmitService.save(perSubmit);

        int databaseSizeBeforeDelete = perSubmitRepository.findAll().size();

        // Get the perSubmit
        restPerSubmitMockMvc.perform(delete("/api/per-submits/{id}", perSubmit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean perSubmitExistsInEs = perSubmitSearchRepository.exists(perSubmit.getId());
        assertThat(perSubmitExistsInEs).isFalse();

        // Validate the database is empty
        List<PerSubmit> perSubmitList = perSubmitRepository.findAll();
        assertThat(perSubmitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerSubmit() throws Exception {
        // Initialize the database
        perSubmitService.save(perSubmit);

        // Search the perSubmit
        restPerSubmitMockMvc.perform(get("/api/_search/per-submits?query=id:" + perSubmit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perSubmit.getId().intValue())))
            .andExpect(jsonPath("$.[*].submitDate").value(hasItem(DEFAULT_SUBMIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dersGrup").value(hasItem(DEFAULT_DERS_GRUP.toString())))
            .andExpect(jsonPath("$.[*].dersSira").value(hasItem(DEFAULT_DERS_SIRA)))
            .andExpect(jsonPath("$.[*].dersAdet").value(hasItem(DEFAULT_DERS_ADET)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerSubmit.class);
        PerSubmit perSubmit1 = new PerSubmit();
        perSubmit1.setId(1L);
        PerSubmit perSubmit2 = new PerSubmit();
        perSubmit2.setId(perSubmit1.getId());
        assertThat(perSubmit1).isEqualTo(perSubmit2);
        perSubmit2.setId(2L);
        assertThat(perSubmit1).isNotEqualTo(perSubmit2);
        perSubmit1.setId(null);
        assertThat(perSubmit1).isNotEqualTo(perSubmit2);
    }
}
