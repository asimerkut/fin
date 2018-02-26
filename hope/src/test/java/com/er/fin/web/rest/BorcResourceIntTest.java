package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.Borc;
import com.er.fin.repository.BorcRepository;
import com.er.fin.service.BorcService;
import com.er.fin.repository.search.BorcSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static com.er.fin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BorcResource REST controller.
 *
 * @see BorcResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class BorcResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ORJINAL_BORC_TUTARI = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORJINAL_BORC_TUTARI = new BigDecimal(2);

    @Autowired
    private BorcRepository borcRepository;

    @Autowired
    private BorcService borcService;

    @Autowired
    private BorcSearchRepository borcSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBorcMockMvc;

    private Borc borc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BorcResource borcResource = new BorcResource(borcService);
        this.restBorcMockMvc = MockMvcBuilders.standaloneSetup(borcResource)
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
    public static Borc createEntity(EntityManager em) {
        Borc borc = new Borc()
            .kod(DEFAULT_KOD)
            .orjinalBorcTutari(DEFAULT_ORJINAL_BORC_TUTARI);
        return borc;
    }

    @Before
    public void initTest() {
        borcSearchRepository.deleteAll();
        borc = createEntity(em);
    }

    @Test
    @Transactional
    public void createBorc() throws Exception {
        int databaseSizeBeforeCreate = borcRepository.findAll().size();

        // Create the Borc
        restBorcMockMvc.perform(post("/api/borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borc)))
            .andExpect(status().isCreated());

        // Validate the Borc in the database
        List<Borc> borcList = borcRepository.findAll();
        assertThat(borcList).hasSize(databaseSizeBeforeCreate + 1);
        Borc testBorc = borcList.get(borcList.size() - 1);
        assertThat(testBorc.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testBorc.getOrjinalBorcTutari()).isEqualTo(DEFAULT_ORJINAL_BORC_TUTARI);

        // Validate the Borc in Elasticsearch
        Borc borcEs = borcSearchRepository.findOne(testBorc.getId());
        assertThat(borcEs).isEqualToIgnoringGivenFields(testBorc);
    }

    @Test
    @Transactional
    public void createBorcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = borcRepository.findAll().size();

        // Create the Borc with an existing ID
        borc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorcMockMvc.perform(post("/api/borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borc)))
            .andExpect(status().isBadRequest());

        // Validate the Borc in the database
        List<Borc> borcList = borcRepository.findAll();
        assertThat(borcList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBorcs() throws Exception {
        // Initialize the database
        borcRepository.saveAndFlush(borc);

        // Get all the borcList
        restBorcMockMvc.perform(get("/api/borcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borc.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].orjinalBorcTutari").value(hasItem(DEFAULT_ORJINAL_BORC_TUTARI.intValue())));
    }

    @Test
    @Transactional
    public void getBorc() throws Exception {
        // Initialize the database
        borcRepository.saveAndFlush(borc);

        // Get the borc
        restBorcMockMvc.perform(get("/api/borcs/{id}", borc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(borc.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.orjinalBorcTutari").value(DEFAULT_ORJINAL_BORC_TUTARI.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBorc() throws Exception {
        // Get the borc
        restBorcMockMvc.perform(get("/api/borcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBorc() throws Exception {
        // Initialize the database
        borcService.save(borc);

        int databaseSizeBeforeUpdate = borcRepository.findAll().size();

        // Update the borc
        Borc updatedBorc = borcRepository.findOne(borc.getId());
        // Disconnect from session so that the updates on updatedBorc are not directly saved in db
        em.detach(updatedBorc);
        updatedBorc
            .kod(UPDATED_KOD)
            .orjinalBorcTutari(UPDATED_ORJINAL_BORC_TUTARI);

        restBorcMockMvc.perform(put("/api/borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBorc)))
            .andExpect(status().isOk());

        // Validate the Borc in the database
        List<Borc> borcList = borcRepository.findAll();
        assertThat(borcList).hasSize(databaseSizeBeforeUpdate);
        Borc testBorc = borcList.get(borcList.size() - 1);
        assertThat(testBorc.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testBorc.getOrjinalBorcTutari()).isEqualTo(UPDATED_ORJINAL_BORC_TUTARI);

        // Validate the Borc in Elasticsearch
        Borc borcEs = borcSearchRepository.findOne(testBorc.getId());
        assertThat(borcEs).isEqualToIgnoringGivenFields(testBorc);
    }

    @Test
    @Transactional
    public void updateNonExistingBorc() throws Exception {
        int databaseSizeBeforeUpdate = borcRepository.findAll().size();

        // Create the Borc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBorcMockMvc.perform(put("/api/borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(borc)))
            .andExpect(status().isCreated());

        // Validate the Borc in the database
        List<Borc> borcList = borcRepository.findAll();
        assertThat(borcList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBorc() throws Exception {
        // Initialize the database
        borcService.save(borc);

        int databaseSizeBeforeDelete = borcRepository.findAll().size();

        // Get the borc
        restBorcMockMvc.perform(delete("/api/borcs/{id}", borc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean borcExistsInEs = borcSearchRepository.exists(borc.getId());
        assertThat(borcExistsInEs).isFalse();

        // Validate the database is empty
        List<Borc> borcList = borcRepository.findAll();
        assertThat(borcList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBorc() throws Exception {
        // Initialize the database
        borcService.save(borc);

        // Search the borc
        restBorcMockMvc.perform(get("/api/_search/borcs?query=id:" + borc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borc.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].orjinalBorcTutari").value(hasItem(DEFAULT_ORJINAL_BORC_TUTARI.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Borc.class);
        Borc borc1 = new Borc();
        borc1.setId(1L);
        Borc borc2 = new Borc();
        borc2.setId(borc1.getId());
        assertThat(borc1).isEqualTo(borc2);
        borc2.setId(2L);
        assertThat(borc1).isNotEqualTo(borc2);
        borc1.setId(null);
        assertThat(borc1).isNotEqualTo(borc2);
    }
}
