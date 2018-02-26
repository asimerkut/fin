package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.IslemKodu;
import com.er.fin.repository.IslemKoduRepository;
import com.er.fin.service.IslemKoduService;
import com.er.fin.repository.search.IslemKoduSearchRepository;
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
 * Test class for the IslemKoduResource REST controller.
 *
 * @see IslemKoduResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class IslemKoduResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    @Autowired
    private IslemKoduRepository islemKoduRepository;

    @Autowired
    private IslemKoduService islemKoduService;

    @Autowired
    private IslemKoduSearchRepository islemKoduSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIslemKoduMockMvc;

    private IslemKodu islemKodu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IslemKoduResource islemKoduResource = new IslemKoduResource(islemKoduService);
        this.restIslemKoduMockMvc = MockMvcBuilders.standaloneSetup(islemKoduResource)
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
    public static IslemKodu createEntity(EntityManager em) {
        IslemKodu islemKodu = new IslemKodu()
            .kod(DEFAULT_KOD);
        return islemKodu;
    }

    @Before
    public void initTest() {
        islemKoduSearchRepository.deleteAll();
        islemKodu = createEntity(em);
    }

    @Test
    @Transactional
    public void createIslemKodu() throws Exception {
        int databaseSizeBeforeCreate = islemKoduRepository.findAll().size();

        // Create the IslemKodu
        restIslemKoduMockMvc.perform(post("/api/islem-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(islemKodu)))
            .andExpect(status().isCreated());

        // Validate the IslemKodu in the database
        List<IslemKodu> islemKoduList = islemKoduRepository.findAll();
        assertThat(islemKoduList).hasSize(databaseSizeBeforeCreate + 1);
        IslemKodu testIslemKodu = islemKoduList.get(islemKoduList.size() - 1);
        assertThat(testIslemKodu.getKod()).isEqualTo(DEFAULT_KOD);

        // Validate the IslemKodu in Elasticsearch
        IslemKodu islemKoduEs = islemKoduSearchRepository.findOne(testIslemKodu.getId());
        assertThat(islemKoduEs).isEqualToIgnoringGivenFields(testIslemKodu);
    }

    @Test
    @Transactional
    public void createIslemKoduWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = islemKoduRepository.findAll().size();

        // Create the IslemKodu with an existing ID
        islemKodu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIslemKoduMockMvc.perform(post("/api/islem-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(islemKodu)))
            .andExpect(status().isBadRequest());

        // Validate the IslemKodu in the database
        List<IslemKodu> islemKoduList = islemKoduRepository.findAll();
        assertThat(islemKoduList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIslemKodus() throws Exception {
        // Initialize the database
        islemKoduRepository.saveAndFlush(islemKodu);

        // Get all the islemKoduList
        restIslemKoduMockMvc.perform(get("/api/islem-kodus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(islemKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void getIslemKodu() throws Exception {
        // Initialize the database
        islemKoduRepository.saveAndFlush(islemKodu);

        // Get the islemKodu
        restIslemKoduMockMvc.perform(get("/api/islem-kodus/{id}", islemKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(islemKodu.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIslemKodu() throws Exception {
        // Get the islemKodu
        restIslemKoduMockMvc.perform(get("/api/islem-kodus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIslemKodu() throws Exception {
        // Initialize the database
        islemKoduService.save(islemKodu);

        int databaseSizeBeforeUpdate = islemKoduRepository.findAll().size();

        // Update the islemKodu
        IslemKodu updatedIslemKodu = islemKoduRepository.findOne(islemKodu.getId());
        // Disconnect from session so that the updates on updatedIslemKodu are not directly saved in db
        em.detach(updatedIslemKodu);
        updatedIslemKodu
            .kod(UPDATED_KOD);

        restIslemKoduMockMvc.perform(put("/api/islem-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIslemKodu)))
            .andExpect(status().isOk());

        // Validate the IslemKodu in the database
        List<IslemKodu> islemKoduList = islemKoduRepository.findAll();
        assertThat(islemKoduList).hasSize(databaseSizeBeforeUpdate);
        IslemKodu testIslemKodu = islemKoduList.get(islemKoduList.size() - 1);
        assertThat(testIslemKodu.getKod()).isEqualTo(UPDATED_KOD);

        // Validate the IslemKodu in Elasticsearch
        IslemKodu islemKoduEs = islemKoduSearchRepository.findOne(testIslemKodu.getId());
        assertThat(islemKoduEs).isEqualToIgnoringGivenFields(testIslemKodu);
    }

    @Test
    @Transactional
    public void updateNonExistingIslemKodu() throws Exception {
        int databaseSizeBeforeUpdate = islemKoduRepository.findAll().size();

        // Create the IslemKodu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIslemKoduMockMvc.perform(put("/api/islem-kodus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(islemKodu)))
            .andExpect(status().isCreated());

        // Validate the IslemKodu in the database
        List<IslemKodu> islemKoduList = islemKoduRepository.findAll();
        assertThat(islemKoduList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIslemKodu() throws Exception {
        // Initialize the database
        islemKoduService.save(islemKodu);

        int databaseSizeBeforeDelete = islemKoduRepository.findAll().size();

        // Get the islemKodu
        restIslemKoduMockMvc.perform(delete("/api/islem-kodus/{id}", islemKodu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean islemKoduExistsInEs = islemKoduSearchRepository.exists(islemKodu.getId());
        assertThat(islemKoduExistsInEs).isFalse();

        // Validate the database is empty
        List<IslemKodu> islemKoduList = islemKoduRepository.findAll();
        assertThat(islemKoduList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIslemKodu() throws Exception {
        // Initialize the database
        islemKoduService.save(islemKodu);

        // Search the islemKodu
        restIslemKoduMockMvc.perform(get("/api/_search/islem-kodus?query=id:" + islemKodu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(islemKodu.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IslemKodu.class);
        IslemKodu islemKodu1 = new IslemKodu();
        islemKodu1.setId(1L);
        IslemKodu islemKodu2 = new IslemKodu();
        islemKodu2.setId(islemKodu1.getId());
        assertThat(islemKodu1).isEqualTo(islemKodu2);
        islemKodu2.setId(2L);
        assertThat(islemKodu1).isNotEqualTo(islemKodu2);
        islemKodu1.setId(null);
        assertThat(islemKodu1).isNotEqualTo(islemKodu2);
    }
}
