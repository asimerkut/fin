package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.Masraf;
import com.er.fin.repository.MasrafRepository;
import com.er.fin.service.MasrafService;
import com.er.fin.repository.search.MasrafSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.er.fin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MasrafResource REST controller.
 *
 * @see MasrafResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class MasrafResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MASRAF_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MASRAF_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_ORJINAL_MASRAF_TUTARI = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORJINAL_MASRAF_TUTARI = new BigDecimal(2);

    @Autowired
    private MasrafRepository masrafRepository;

    @Autowired
    private MasrafService masrafService;

    @Autowired
    private MasrafSearchRepository masrafSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMasrafMockMvc;

    private Masraf masraf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MasrafResource masrafResource = new MasrafResource(masrafService);
        this.restMasrafMockMvc = MockMvcBuilders.standaloneSetup(masrafResource)
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
    public static Masraf createEntity(EntityManager em) {
        Masraf masraf = new Masraf()
            .kod(DEFAULT_KOD)
            .masrafTarihi(DEFAULT_MASRAF_TARIHI)
            .orjinalMasrafTutari(DEFAULT_ORJINAL_MASRAF_TUTARI);
        return masraf;
    }

    @Before
    public void initTest() {
        masrafSearchRepository.deleteAll();
        masraf = createEntity(em);
    }

    @Test
    @Transactional
    public void createMasraf() throws Exception {
        int databaseSizeBeforeCreate = masrafRepository.findAll().size();

        // Create the Masraf
        restMasrafMockMvc.perform(post("/api/masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masraf)))
            .andExpect(status().isCreated());

        // Validate the Masraf in the database
        List<Masraf> masrafList = masrafRepository.findAll();
        assertThat(masrafList).hasSize(databaseSizeBeforeCreate + 1);
        Masraf testMasraf = masrafList.get(masrafList.size() - 1);
        assertThat(testMasraf.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testMasraf.getMasrafTarihi()).isEqualTo(DEFAULT_MASRAF_TARIHI);
        assertThat(testMasraf.getOrjinalMasrafTutari()).isEqualTo(DEFAULT_ORJINAL_MASRAF_TUTARI);

        // Validate the Masraf in Elasticsearch
        Masraf masrafEs = masrafSearchRepository.findOne(testMasraf.getId());
        assertThat(masrafEs).isEqualToIgnoringGivenFields(testMasraf);
    }

    @Test
    @Transactional
    public void createMasrafWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = masrafRepository.findAll().size();

        // Create the Masraf with an existing ID
        masraf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMasrafMockMvc.perform(post("/api/masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masraf)))
            .andExpect(status().isBadRequest());

        // Validate the Masraf in the database
        List<Masraf> masrafList = masrafRepository.findAll();
        assertThat(masrafList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMasrafs() throws Exception {
        // Initialize the database
        masrafRepository.saveAndFlush(masraf);

        // Get all the masrafList
        restMasrafMockMvc.perform(get("/api/masrafs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masraf.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].masrafTarihi").value(hasItem(DEFAULT_MASRAF_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].orjinalMasrafTutari").value(hasItem(DEFAULT_ORJINAL_MASRAF_TUTARI.intValue())));
    }

    @Test
    @Transactional
    public void getMasraf() throws Exception {
        // Initialize the database
        masrafRepository.saveAndFlush(masraf);

        // Get the masraf
        restMasrafMockMvc.perform(get("/api/masrafs/{id}", masraf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(masraf.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.masrafTarihi").value(DEFAULT_MASRAF_TARIHI.toString()))
            .andExpect(jsonPath("$.orjinalMasrafTutari").value(DEFAULT_ORJINAL_MASRAF_TUTARI.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMasraf() throws Exception {
        // Get the masraf
        restMasrafMockMvc.perform(get("/api/masrafs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMasraf() throws Exception {
        // Initialize the database
        masrafService.save(masraf);

        int databaseSizeBeforeUpdate = masrafRepository.findAll().size();

        // Update the masraf
        Masraf updatedMasraf = masrafRepository.findOne(masraf.getId());
        // Disconnect from session so that the updates on updatedMasraf are not directly saved in db
        em.detach(updatedMasraf);
        updatedMasraf
            .kod(UPDATED_KOD)
            .masrafTarihi(UPDATED_MASRAF_TARIHI)
            .orjinalMasrafTutari(UPDATED_ORJINAL_MASRAF_TUTARI);

        restMasrafMockMvc.perform(put("/api/masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMasraf)))
            .andExpect(status().isOk());

        // Validate the Masraf in the database
        List<Masraf> masrafList = masrafRepository.findAll();
        assertThat(masrafList).hasSize(databaseSizeBeforeUpdate);
        Masraf testMasraf = masrafList.get(masrafList.size() - 1);
        assertThat(testMasraf.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testMasraf.getMasrafTarihi()).isEqualTo(UPDATED_MASRAF_TARIHI);
        assertThat(testMasraf.getOrjinalMasrafTutari()).isEqualTo(UPDATED_ORJINAL_MASRAF_TUTARI);

        // Validate the Masraf in Elasticsearch
        Masraf masrafEs = masrafSearchRepository.findOne(testMasraf.getId());
        assertThat(masrafEs).isEqualToIgnoringGivenFields(testMasraf);
    }

    @Test
    @Transactional
    public void updateNonExistingMasraf() throws Exception {
        int databaseSizeBeforeUpdate = masrafRepository.findAll().size();

        // Create the Masraf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMasrafMockMvc.perform(put("/api/masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(masraf)))
            .andExpect(status().isCreated());

        // Validate the Masraf in the database
        List<Masraf> masrafList = masrafRepository.findAll();
        assertThat(masrafList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMasraf() throws Exception {
        // Initialize the database
        masrafService.save(masraf);

        int databaseSizeBeforeDelete = masrafRepository.findAll().size();

        // Get the masraf
        restMasrafMockMvc.perform(delete("/api/masrafs/{id}", masraf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean masrafExistsInEs = masrafSearchRepository.exists(masraf.getId());
        assertThat(masrafExistsInEs).isFalse();

        // Validate the database is empty
        List<Masraf> masrafList = masrafRepository.findAll();
        assertThat(masrafList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMasraf() throws Exception {
        // Initialize the database
        masrafService.save(masraf);

        // Search the masraf
        restMasrafMockMvc.perform(get("/api/_search/masrafs?query=id:" + masraf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masraf.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].masrafTarihi").value(hasItem(DEFAULT_MASRAF_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].orjinalMasrafTutari").value(hasItem(DEFAULT_ORJINAL_MASRAF_TUTARI.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Masraf.class);
        Masraf masraf1 = new Masraf();
        masraf1.setId(1L);
        Masraf masraf2 = new Masraf();
        masraf2.setId(masraf1.getId());
        assertThat(masraf1).isEqualTo(masraf2);
        masraf2.setId(2L);
        assertThat(masraf1).isNotEqualTo(masraf2);
        masraf1.setId(null);
        assertThat(masraf1).isNotEqualTo(masraf2);
    }
}
