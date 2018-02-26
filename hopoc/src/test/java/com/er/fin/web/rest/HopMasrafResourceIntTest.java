package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.HopMasraf;
import com.er.fin.repository.HopMasrafRepository;
import com.er.fin.service.HopMasrafService;
import com.er.fin.repository.search.HopMasrafSearchRepository;
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

import com.er.fin.domain.enumeration.EnmMasrafTipi;
/**
 * Test class for the HopMasrafResource REST controller.
 *
 * @see HopMasrafResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class HopMasrafResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_TUTAR = new BigDecimal(2);

    private static final EnmMasrafTipi DEFAULT_MASRAF_TIPI = EnmMasrafTipi.KLASOR;
    private static final EnmMasrafTipi UPDATED_MASRAF_TIPI = EnmMasrafTipi.KLASOR_BSMV;

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HopMasrafRepository hopMasrafRepository;

    @Autowired
    private HopMasrafService hopMasrafService;

    @Autowired
    private HopMasrafSearchRepository hopMasrafSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHopMasrafMockMvc;

    private HopMasraf hopMasraf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HopMasrafResource hopMasrafResource = new HopMasrafResource(hopMasrafService);
        this.restHopMasrafMockMvc = MockMvcBuilders.standaloneSetup(hopMasrafResource)
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
    public static HopMasraf createEntity(EntityManager em) {
        HopMasraf hopMasraf = new HopMasraf()
            .kod(DEFAULT_KOD)
            .tutar(DEFAULT_TUTAR)
            .masrafTipi(DEFAULT_MASRAF_TIPI)
            .tarih(DEFAULT_TARIH);
        return hopMasraf;
    }

    @Before
    public void initTest() {
        hopMasrafSearchRepository.deleteAll();
        hopMasraf = createEntity(em);
    }

    @Test
    @Transactional
    public void createHopMasraf() throws Exception {
        int databaseSizeBeforeCreate = hopMasrafRepository.findAll().size();

        // Create the HopMasraf
        restHopMasrafMockMvc.perform(post("/api/hop-masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopMasraf)))
            .andExpect(status().isCreated());

        // Validate the HopMasraf in the database
        List<HopMasraf> hopMasrafList = hopMasrafRepository.findAll();
        assertThat(hopMasrafList).hasSize(databaseSizeBeforeCreate + 1);
        HopMasraf testHopMasraf = hopMasrafList.get(hopMasrafList.size() - 1);
        assertThat(testHopMasraf.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testHopMasraf.getTutar()).isEqualTo(DEFAULT_TUTAR);
        assertThat(testHopMasraf.getMasrafTipi()).isEqualTo(DEFAULT_MASRAF_TIPI);
        assertThat(testHopMasraf.getTarih()).isEqualTo(DEFAULT_TARIH);

        // Validate the HopMasraf in Elasticsearch
        HopMasraf hopMasrafEs = hopMasrafSearchRepository.findOne(testHopMasraf.getId());
        assertThat(hopMasrafEs).isEqualToIgnoringGivenFields(testHopMasraf);
    }

    @Test
    @Transactional
    public void createHopMasrafWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hopMasrafRepository.findAll().size();

        // Create the HopMasraf with an existing ID
        hopMasraf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHopMasrafMockMvc.perform(post("/api/hop-masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopMasraf)))
            .andExpect(status().isBadRequest());

        // Validate the HopMasraf in the database
        List<HopMasraf> hopMasrafList = hopMasrafRepository.findAll();
        assertThat(hopMasrafList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHopMasrafs() throws Exception {
        // Initialize the database
        hopMasrafRepository.saveAndFlush(hopMasraf);

        // Get all the hopMasrafList
        restHopMasrafMockMvc.perform(get("/api/hop-masrafs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopMasraf.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].masrafTipi").value(hasItem(DEFAULT_MASRAF_TIPI.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())));
    }

    @Test
    @Transactional
    public void getHopMasraf() throws Exception {
        // Initialize the database
        hopMasrafRepository.saveAndFlush(hopMasraf);

        // Get the hopMasraf
        restHopMasrafMockMvc.perform(get("/api/hop-masrafs/{id}", hopMasraf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hopMasraf.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.tutar").value(DEFAULT_TUTAR.intValue()))
            .andExpect(jsonPath("$.masrafTipi").value(DEFAULT_MASRAF_TIPI.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHopMasraf() throws Exception {
        // Get the hopMasraf
        restHopMasrafMockMvc.perform(get("/api/hop-masrafs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHopMasraf() throws Exception {
        // Initialize the database
        hopMasrafService.save(hopMasraf);

        int databaseSizeBeforeUpdate = hopMasrafRepository.findAll().size();

        // Update the hopMasraf
        HopMasraf updatedHopMasraf = hopMasrafRepository.findOne(hopMasraf.getId());
        // Disconnect from session so that the updates on updatedHopMasraf are not directly saved in db
        em.detach(updatedHopMasraf);
        updatedHopMasraf
            .kod(UPDATED_KOD)
            .tutar(UPDATED_TUTAR)
            .masrafTipi(UPDATED_MASRAF_TIPI)
            .tarih(UPDATED_TARIH);

        restHopMasrafMockMvc.perform(put("/api/hop-masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHopMasraf)))
            .andExpect(status().isOk());

        // Validate the HopMasraf in the database
        List<HopMasraf> hopMasrafList = hopMasrafRepository.findAll();
        assertThat(hopMasrafList).hasSize(databaseSizeBeforeUpdate);
        HopMasraf testHopMasraf = hopMasrafList.get(hopMasrafList.size() - 1);
        assertThat(testHopMasraf.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testHopMasraf.getTutar()).isEqualTo(UPDATED_TUTAR);
        assertThat(testHopMasraf.getMasrafTipi()).isEqualTo(UPDATED_MASRAF_TIPI);
        assertThat(testHopMasraf.getTarih()).isEqualTo(UPDATED_TARIH);

        // Validate the HopMasraf in Elasticsearch
        HopMasraf hopMasrafEs = hopMasrafSearchRepository.findOne(testHopMasraf.getId());
        assertThat(hopMasrafEs).isEqualToIgnoringGivenFields(testHopMasraf);
    }

    @Test
    @Transactional
    public void updateNonExistingHopMasraf() throws Exception {
        int databaseSizeBeforeUpdate = hopMasrafRepository.findAll().size();

        // Create the HopMasraf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHopMasrafMockMvc.perform(put("/api/hop-masrafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopMasraf)))
            .andExpect(status().isCreated());

        // Validate the HopMasraf in the database
        List<HopMasraf> hopMasrafList = hopMasrafRepository.findAll();
        assertThat(hopMasrafList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHopMasraf() throws Exception {
        // Initialize the database
        hopMasrafService.save(hopMasraf);

        int databaseSizeBeforeDelete = hopMasrafRepository.findAll().size();

        // Get the hopMasraf
        restHopMasrafMockMvc.perform(delete("/api/hop-masrafs/{id}", hopMasraf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hopMasrafExistsInEs = hopMasrafSearchRepository.exists(hopMasraf.getId());
        assertThat(hopMasrafExistsInEs).isFalse();

        // Validate the database is empty
        List<HopMasraf> hopMasrafList = hopMasrafRepository.findAll();
        assertThat(hopMasrafList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHopMasraf() throws Exception {
        // Initialize the database
        hopMasrafService.save(hopMasraf);

        // Search the hopMasraf
        restHopMasrafMockMvc.perform(get("/api/_search/hop-masrafs?query=id:" + hopMasraf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopMasraf.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].masrafTipi").value(hasItem(DEFAULT_MASRAF_TIPI.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopMasraf.class);
        HopMasraf hopMasraf1 = new HopMasraf();
        hopMasraf1.setId(1L);
        HopMasraf hopMasraf2 = new HopMasraf();
        hopMasraf2.setId(hopMasraf1.getId());
        assertThat(hopMasraf1).isEqualTo(hopMasraf2);
        hopMasraf2.setId(2L);
        assertThat(hopMasraf1).isNotEqualTo(hopMasraf2);
        hopMasraf1.setId(null);
        assertThat(hopMasraf1).isNotEqualTo(hopMasraf2);
    }
}
