package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.DefAnswer;
import com.er.fin.domain.DefRelation;
import com.er.fin.domain.DefItem;
import com.er.fin.repository.DefAnswerRepository;
import com.er.fin.service.DefAnswerService;
import com.er.fin.repository.search.DefAnswerSearchRepository;
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
 * Test class for the DefAnswerResource REST controller.
 *
 * @see DefAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class DefAnswerResourceIntTest {

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Autowired
    private DefAnswerRepository defAnswerRepository;

    @Autowired
    private DefAnswerService defAnswerService;

    @Autowired
    private DefAnswerSearchRepository defAnswerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDefAnswerMockMvc;

    private DefAnswer defAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DefAnswerResource defAnswerResource = new DefAnswerResource(defAnswerService);
        this.restDefAnswerMockMvc = MockMvcBuilders.standaloneSetup(defAnswerResource)
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
    public static DefAnswer createEntity(EntityManager em) {
        DefAnswer defAnswer = new DefAnswer()
            .answer(DEFAULT_ANSWER);
        // Add required entity
        DefRelation relation = DefRelationResourceIntTest.createEntity(em);
        em.persist(relation);
        em.flush();
        defAnswer.setRelation(relation);
        // Add required entity
        DefItem itemSource = DefItemResourceIntTest.createEntity(em);
        em.persist(itemSource);
        em.flush();
        defAnswer.setItemSource(itemSource);
        return defAnswer;
    }

    @Before
    public void initTest() {
        defAnswerSearchRepository.deleteAll();
        defAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createDefAnswer() throws Exception {
        int databaseSizeBeforeCreate = defAnswerRepository.findAll().size();

        // Create the DefAnswer
        restDefAnswerMockMvc.perform(post("/api/def-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defAnswer)))
            .andExpect(status().isCreated());

        // Validate the DefAnswer in the database
        List<DefAnswer> defAnswerList = defAnswerRepository.findAll();
        assertThat(defAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        DefAnswer testDefAnswer = defAnswerList.get(defAnswerList.size() - 1);
        assertThat(testDefAnswer.getAnswer()).isEqualTo(DEFAULT_ANSWER);

        // Validate the DefAnswer in Elasticsearch
        DefAnswer defAnswerEs = defAnswerSearchRepository.findOne(testDefAnswer.getId());
        assertThat(defAnswerEs).isEqualToIgnoringGivenFields(testDefAnswer);
    }

    @Test
    @Transactional
    public void createDefAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = defAnswerRepository.findAll().size();

        // Create the DefAnswer with an existing ID
        defAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefAnswerMockMvc.perform(post("/api/def-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the DefAnswer in the database
        List<DefAnswer> defAnswerList = defAnswerRepository.findAll();
        assertThat(defAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDefAnswers() throws Exception {
        // Initialize the database
        defAnswerRepository.saveAndFlush(defAnswer);

        // Get all the defAnswerList
        restDefAnswerMockMvc.perform(get("/api/def-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    @Transactional
    public void getDefAnswer() throws Exception {
        // Initialize the database
        defAnswerRepository.saveAndFlush(defAnswer);

        // Get the defAnswer
        restDefAnswerMockMvc.perform(get("/api/def-answers/{id}", defAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(defAnswer.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDefAnswer() throws Exception {
        // Get the defAnswer
        restDefAnswerMockMvc.perform(get("/api/def-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefAnswer() throws Exception {
        // Initialize the database
        defAnswerService.save(defAnswer);

        int databaseSizeBeforeUpdate = defAnswerRepository.findAll().size();

        // Update the defAnswer
        DefAnswer updatedDefAnswer = defAnswerRepository.findOne(defAnswer.getId());
        // Disconnect from session so that the updates on updatedDefAnswer are not directly saved in db
        em.detach(updatedDefAnswer);
        updatedDefAnswer
            .answer(UPDATED_ANSWER);

        restDefAnswerMockMvc.perform(put("/api/def-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDefAnswer)))
            .andExpect(status().isOk());

        // Validate the DefAnswer in the database
        List<DefAnswer> defAnswerList = defAnswerRepository.findAll();
        assertThat(defAnswerList).hasSize(databaseSizeBeforeUpdate);
        DefAnswer testDefAnswer = defAnswerList.get(defAnswerList.size() - 1);
        assertThat(testDefAnswer.getAnswer()).isEqualTo(UPDATED_ANSWER);

        // Validate the DefAnswer in Elasticsearch
        DefAnswer defAnswerEs = defAnswerSearchRepository.findOne(testDefAnswer.getId());
        assertThat(defAnswerEs).isEqualToIgnoringGivenFields(testDefAnswer);
    }

    @Test
    @Transactional
    public void updateNonExistingDefAnswer() throws Exception {
        int databaseSizeBeforeUpdate = defAnswerRepository.findAll().size();

        // Create the DefAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDefAnswerMockMvc.perform(put("/api/def-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defAnswer)))
            .andExpect(status().isCreated());

        // Validate the DefAnswer in the database
        List<DefAnswer> defAnswerList = defAnswerRepository.findAll();
        assertThat(defAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDefAnswer() throws Exception {
        // Initialize the database
        defAnswerService.save(defAnswer);

        int databaseSizeBeforeDelete = defAnswerRepository.findAll().size();

        // Get the defAnswer
        restDefAnswerMockMvc.perform(delete("/api/def-answers/{id}", defAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean defAnswerExistsInEs = defAnswerSearchRepository.exists(defAnswer.getId());
        assertThat(defAnswerExistsInEs).isFalse();

        // Validate the database is empty
        List<DefAnswer> defAnswerList = defAnswerRepository.findAll();
        assertThat(defAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDefAnswer() throws Exception {
        // Initialize the database
        defAnswerService.save(defAnswer);

        // Search the defAnswer
        restDefAnswerMockMvc.perform(get("/api/_search/def-answers?query=id:" + defAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefAnswer.class);
        DefAnswer defAnswer1 = new DefAnswer();
        defAnswer1.setId(1L);
        DefAnswer defAnswer2 = new DefAnswer();
        defAnswer2.setId(defAnswer1.getId());
        assertThat(defAnswer1).isEqualTo(defAnswer2);
        defAnswer2.setId(2L);
        assertThat(defAnswer1).isNotEqualTo(defAnswer2);
        defAnswer1.setId(null);
        assertThat(defAnswer1).isNotEqualTo(defAnswer2);
    }
}
