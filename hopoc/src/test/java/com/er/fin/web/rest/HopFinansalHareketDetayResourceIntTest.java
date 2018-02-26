package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.HopFinansalHareketDetay;
import com.er.fin.repository.HopFinansalHareketDetayRepository;
import com.er.fin.service.HopFinansalHareketDetayService;
import com.er.fin.repository.search.HopFinansalHareketDetaySearchRepository;
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

import com.er.fin.domain.enumeration.EnmHesap;
import com.er.fin.domain.enumeration.EnmHesap;
/**
 * Test class for the HopFinansalHareketDetayResource REST controller.
 *
 * @see HopFinansalHareketDetayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class HopFinansalHareketDetayResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final Long DEFAULT_HESAP_YONU = 1L;
    private static final Long UPDATED_HESAP_YONU = 2L;

    private static final EnmHesap DEFAULT_HESAP = EnmHesap.DB;
    private static final EnmHesap UPDATED_HESAP = EnmHesap.DB_00;

    private static final EnmHesap DEFAULT_KARSI_HESAP = EnmHesap.DB;
    private static final EnmHesap UPDATED_KARSI_HESAP = EnmHesap.DB_00;

    private static final BigDecimal DEFAULT_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_TUTAR = new BigDecimal(2);

    @Autowired
    private HopFinansalHareketDetayRepository hopFinansalHareketDetayRepository;

    @Autowired
    private HopFinansalHareketDetayService hopFinansalHareketDetayService;

    @Autowired
    private HopFinansalHareketDetaySearchRepository hopFinansalHareketDetaySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHopFinansalHareketDetayMockMvc;

    private HopFinansalHareketDetay hopFinansalHareketDetay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HopFinansalHareketDetayResource hopFinansalHareketDetayResource = new HopFinansalHareketDetayResource(hopFinansalHareketDetayService);
        this.restHopFinansalHareketDetayMockMvc = MockMvcBuilders.standaloneSetup(hopFinansalHareketDetayResource)
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
    public static HopFinansalHareketDetay createEntity(EntityManager em) {
        HopFinansalHareketDetay hopFinansalHareketDetay = new HopFinansalHareketDetay()
            .kod(DEFAULT_KOD)
            .hesapYonu(DEFAULT_HESAP_YONU)
            .hesap(DEFAULT_HESAP)
            .karsiHesap(DEFAULT_KARSI_HESAP)
            .tutar(DEFAULT_TUTAR);
        return hopFinansalHareketDetay;
    }

    @Before
    public void initTest() {
        hopFinansalHareketDetaySearchRepository.deleteAll();
        hopFinansalHareketDetay = createEntity(em);
    }

    @Test
    @Transactional
    public void createHopFinansalHareketDetay() throws Exception {
        int databaseSizeBeforeCreate = hopFinansalHareketDetayRepository.findAll().size();

        // Create the HopFinansalHareketDetay
        restHopFinansalHareketDetayMockMvc.perform(post("/api/hop-finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopFinansalHareketDetay)))
            .andExpect(status().isCreated());

        // Validate the HopFinansalHareketDetay in the database
        List<HopFinansalHareketDetay> hopFinansalHareketDetayList = hopFinansalHareketDetayRepository.findAll();
        assertThat(hopFinansalHareketDetayList).hasSize(databaseSizeBeforeCreate + 1);
        HopFinansalHareketDetay testHopFinansalHareketDetay = hopFinansalHareketDetayList.get(hopFinansalHareketDetayList.size() - 1);
        assertThat(testHopFinansalHareketDetay.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testHopFinansalHareketDetay.getHesapYonu()).isEqualTo(DEFAULT_HESAP_YONU);
        assertThat(testHopFinansalHareketDetay.getHesap()).isEqualTo(DEFAULT_HESAP);
        assertThat(testHopFinansalHareketDetay.getKarsiHesap()).isEqualTo(DEFAULT_KARSI_HESAP);
        assertThat(testHopFinansalHareketDetay.getTutar()).isEqualTo(DEFAULT_TUTAR);

        // Validate the HopFinansalHareketDetay in Elasticsearch
        HopFinansalHareketDetay hopFinansalHareketDetayEs = hopFinansalHareketDetaySearchRepository.findOne(testHopFinansalHareketDetay.getId());
        assertThat(hopFinansalHareketDetayEs).isEqualToIgnoringGivenFields(testHopFinansalHareketDetay);
    }

    @Test
    @Transactional
    public void createHopFinansalHareketDetayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hopFinansalHareketDetayRepository.findAll().size();

        // Create the HopFinansalHareketDetay with an existing ID
        hopFinansalHareketDetay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHopFinansalHareketDetayMockMvc.perform(post("/api/hop-finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopFinansalHareketDetay)))
            .andExpect(status().isBadRequest());

        // Validate the HopFinansalHareketDetay in the database
        List<HopFinansalHareketDetay> hopFinansalHareketDetayList = hopFinansalHareketDetayRepository.findAll();
        assertThat(hopFinansalHareketDetayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHopFinansalHareketDetays() throws Exception {
        // Initialize the database
        hopFinansalHareketDetayRepository.saveAndFlush(hopFinansalHareketDetay);

        // Get all the hopFinansalHareketDetayList
        restHopFinansalHareketDetayMockMvc.perform(get("/api/hop-finansal-hareket-detays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopFinansalHareketDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].hesapYonu").value(hasItem(DEFAULT_HESAP_YONU.intValue())))
            .andExpect(jsonPath("$.[*].hesap").value(hasItem(DEFAULT_HESAP.toString())))
            .andExpect(jsonPath("$.[*].karsiHesap").value(hasItem(DEFAULT_KARSI_HESAP.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())));
    }

    @Test
    @Transactional
    public void getHopFinansalHareketDetay() throws Exception {
        // Initialize the database
        hopFinansalHareketDetayRepository.saveAndFlush(hopFinansalHareketDetay);

        // Get the hopFinansalHareketDetay
        restHopFinansalHareketDetayMockMvc.perform(get("/api/hop-finansal-hareket-detays/{id}", hopFinansalHareketDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hopFinansalHareketDetay.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.hesapYonu").value(DEFAULT_HESAP_YONU.intValue()))
            .andExpect(jsonPath("$.hesap").value(DEFAULT_HESAP.toString()))
            .andExpect(jsonPath("$.karsiHesap").value(DEFAULT_KARSI_HESAP.toString()))
            .andExpect(jsonPath("$.tutar").value(DEFAULT_TUTAR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHopFinansalHareketDetay() throws Exception {
        // Get the hopFinansalHareketDetay
        restHopFinansalHareketDetayMockMvc.perform(get("/api/hop-finansal-hareket-detays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHopFinansalHareketDetay() throws Exception {
        // Initialize the database
        hopFinansalHareketDetayService.save(hopFinansalHareketDetay);

        int databaseSizeBeforeUpdate = hopFinansalHareketDetayRepository.findAll().size();

        // Update the hopFinansalHareketDetay
        HopFinansalHareketDetay updatedHopFinansalHareketDetay = hopFinansalHareketDetayRepository.findOne(hopFinansalHareketDetay.getId());
        // Disconnect from session so that the updates on updatedHopFinansalHareketDetay are not directly saved in db
        em.detach(updatedHopFinansalHareketDetay);
        updatedHopFinansalHareketDetay
            .kod(UPDATED_KOD)
            .hesapYonu(UPDATED_HESAP_YONU)
            .hesap(UPDATED_HESAP)
            .karsiHesap(UPDATED_KARSI_HESAP)
            .tutar(UPDATED_TUTAR);

        restHopFinansalHareketDetayMockMvc.perform(put("/api/hop-finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHopFinansalHareketDetay)))
            .andExpect(status().isOk());

        // Validate the HopFinansalHareketDetay in the database
        List<HopFinansalHareketDetay> hopFinansalHareketDetayList = hopFinansalHareketDetayRepository.findAll();
        assertThat(hopFinansalHareketDetayList).hasSize(databaseSizeBeforeUpdate);
        HopFinansalHareketDetay testHopFinansalHareketDetay = hopFinansalHareketDetayList.get(hopFinansalHareketDetayList.size() - 1);
        assertThat(testHopFinansalHareketDetay.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testHopFinansalHareketDetay.getHesapYonu()).isEqualTo(UPDATED_HESAP_YONU);
        assertThat(testHopFinansalHareketDetay.getHesap()).isEqualTo(UPDATED_HESAP);
        assertThat(testHopFinansalHareketDetay.getKarsiHesap()).isEqualTo(UPDATED_KARSI_HESAP);
        assertThat(testHopFinansalHareketDetay.getTutar()).isEqualTo(UPDATED_TUTAR);

        // Validate the HopFinansalHareketDetay in Elasticsearch
        HopFinansalHareketDetay hopFinansalHareketDetayEs = hopFinansalHareketDetaySearchRepository.findOne(testHopFinansalHareketDetay.getId());
        assertThat(hopFinansalHareketDetayEs).isEqualToIgnoringGivenFields(testHopFinansalHareketDetay);
    }

    @Test
    @Transactional
    public void updateNonExistingHopFinansalHareketDetay() throws Exception {
        int databaseSizeBeforeUpdate = hopFinansalHareketDetayRepository.findAll().size();

        // Create the HopFinansalHareketDetay

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHopFinansalHareketDetayMockMvc.perform(put("/api/hop-finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopFinansalHareketDetay)))
            .andExpect(status().isCreated());

        // Validate the HopFinansalHareketDetay in the database
        List<HopFinansalHareketDetay> hopFinansalHareketDetayList = hopFinansalHareketDetayRepository.findAll();
        assertThat(hopFinansalHareketDetayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHopFinansalHareketDetay() throws Exception {
        // Initialize the database
        hopFinansalHareketDetayService.save(hopFinansalHareketDetay);

        int databaseSizeBeforeDelete = hopFinansalHareketDetayRepository.findAll().size();

        // Get the hopFinansalHareketDetay
        restHopFinansalHareketDetayMockMvc.perform(delete("/api/hop-finansal-hareket-detays/{id}", hopFinansalHareketDetay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hopFinansalHareketDetayExistsInEs = hopFinansalHareketDetaySearchRepository.exists(hopFinansalHareketDetay.getId());
        assertThat(hopFinansalHareketDetayExistsInEs).isFalse();

        // Validate the database is empty
        List<HopFinansalHareketDetay> hopFinansalHareketDetayList = hopFinansalHareketDetayRepository.findAll();
        assertThat(hopFinansalHareketDetayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHopFinansalHareketDetay() throws Exception {
        // Initialize the database
        hopFinansalHareketDetayService.save(hopFinansalHareketDetay);

        // Search the hopFinansalHareketDetay
        restHopFinansalHareketDetayMockMvc.perform(get("/api/_search/hop-finansal-hareket-detays?query=id:" + hopFinansalHareketDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopFinansalHareketDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].hesapYonu").value(hasItem(DEFAULT_HESAP_YONU.intValue())))
            .andExpect(jsonPath("$.[*].hesap").value(hasItem(DEFAULT_HESAP.toString())))
            .andExpect(jsonPath("$.[*].karsiHesap").value(hasItem(DEFAULT_KARSI_HESAP.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopFinansalHareketDetay.class);
        HopFinansalHareketDetay hopFinansalHareketDetay1 = new HopFinansalHareketDetay();
        hopFinansalHareketDetay1.setId(1L);
        HopFinansalHareketDetay hopFinansalHareketDetay2 = new HopFinansalHareketDetay();
        hopFinansalHareketDetay2.setId(hopFinansalHareketDetay1.getId());
        assertThat(hopFinansalHareketDetay1).isEqualTo(hopFinansalHareketDetay2);
        hopFinansalHareketDetay2.setId(2L);
        assertThat(hopFinansalHareketDetay1).isNotEqualTo(hopFinansalHareketDetay2);
        hopFinansalHareketDetay1.setId(null);
        assertThat(hopFinansalHareketDetay1).isNotEqualTo(hopFinansalHareketDetay2);
    }
}
