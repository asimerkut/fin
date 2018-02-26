package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.FinansalHareketDetay;
import com.er.fin.repository.FinansalHareketDetayRepository;
import com.er.fin.service.FinansalHareketDetayService;
import com.er.fin.repository.search.FinansalHareketDetaySearchRepository;
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

import com.er.fin.domain.enumeration.HesapEnum;
import com.er.fin.domain.enumeration.HesapEnum;
/**
 * Test class for the FinansalHareketDetayResource REST controller.
 *
 * @see FinansalHareketDetayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class FinansalHareketDetayResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ISLEM_TUTARI = new BigDecimal(1);
    private static final BigDecimal UPDATED_ISLEM_TUTARI = new BigDecimal(2);

    private static final Long DEFAULT_HESAP_YONU = 1L;
    private static final Long UPDATED_HESAP_YONU = 2L;

    private static final HesapEnum DEFAULT_HESAP_ID = HesapEnum.DB;

    private static final HesapEnum UPDATED_HESAP_ID = HesapEnum.DB_10;

    private static final HesapEnum DEFAULT_KARSI_HESAP_ID = HesapEnum.DB;
    private static final HesapEnum UPDATED_KARSI_HESAP_ID = HesapEnum.DB_10;

    @Autowired
    private FinansalHareketDetayRepository finansalHareketDetayRepository;

    @Autowired
    private FinansalHareketDetayService finansalHareketDetayService;

    @Autowired
    private FinansalHareketDetaySearchRepository finansalHareketDetaySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFinansalHareketDetayMockMvc;

    private FinansalHareketDetay finansalHareketDetay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinansalHareketDetayResource finansalHareketDetayResource = new FinansalHareketDetayResource(finansalHareketDetayService);
        this.restFinansalHareketDetayMockMvc = MockMvcBuilders.standaloneSetup(finansalHareketDetayResource)
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
    public static FinansalHareketDetay createEntity(EntityManager em) {
        FinansalHareketDetay finansalHareketDetay = new FinansalHareketDetay()
            .kod(DEFAULT_KOD)
            .islemTutari(DEFAULT_ISLEM_TUTARI)
            .hesapYonu(DEFAULT_HESAP_YONU)
            .hesapId(DEFAULT_HESAP_ID)
            .karsiHesapId(DEFAULT_KARSI_HESAP_ID);
        return finansalHareketDetay;
    }

    @Before
    public void initTest() {
        finansalHareketDetaySearchRepository.deleteAll();
        finansalHareketDetay = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinansalHareketDetay() throws Exception {
        int databaseSizeBeforeCreate = finansalHareketDetayRepository.findAll().size();

        // Create the FinansalHareketDetay
        restFinansalHareketDetayMockMvc.perform(post("/api/finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalHareketDetay)))
            .andExpect(status().isCreated());

        // Validate the FinansalHareketDetay in the database
        List<FinansalHareketDetay> finansalHareketDetayList = finansalHareketDetayRepository.findAll();
        assertThat(finansalHareketDetayList).hasSize(databaseSizeBeforeCreate + 1);
        FinansalHareketDetay testFinansalHareketDetay = finansalHareketDetayList.get(finansalHareketDetayList.size() - 1);
        assertThat(testFinansalHareketDetay.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testFinansalHareketDetay.getIslemTutari()).isEqualTo(DEFAULT_ISLEM_TUTARI);
        assertThat(testFinansalHareketDetay.getHesapYonu()).isEqualTo(DEFAULT_HESAP_YONU);
        assertThat(testFinansalHareketDetay.getHesapId()).isEqualTo(DEFAULT_HESAP_ID);
        assertThat(testFinansalHareketDetay.getKarsiHesapId()).isEqualTo(DEFAULT_KARSI_HESAP_ID);

        // Validate the FinansalHareketDetay in Elasticsearch
        FinansalHareketDetay finansalHareketDetayEs = finansalHareketDetaySearchRepository.findOne(testFinansalHareketDetay.getId());
        assertThat(finansalHareketDetayEs).isEqualToIgnoringGivenFields(testFinansalHareketDetay);
    }

    @Test
    @Transactional
    public void createFinansalHareketDetayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = finansalHareketDetayRepository.findAll().size();

        // Create the FinansalHareketDetay with an existing ID
        finansalHareketDetay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinansalHareketDetayMockMvc.perform(post("/api/finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalHareketDetay)))
            .andExpect(status().isBadRequest());

        // Validate the FinansalHareketDetay in the database
        List<FinansalHareketDetay> finansalHareketDetayList = finansalHareketDetayRepository.findAll();
        assertThat(finansalHareketDetayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFinansalHareketDetays() throws Exception {
        // Initialize the database
        finansalHareketDetayRepository.saveAndFlush(finansalHareketDetay);

        // Get all the finansalHareketDetayList
        restFinansalHareketDetayMockMvc.perform(get("/api/finansal-hareket-detays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finansalHareketDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].islemTutari").value(hasItem(DEFAULT_ISLEM_TUTARI.intValue())))
            .andExpect(jsonPath("$.[*].hesapYonu").value(hasItem(DEFAULT_HESAP_YONU.intValue())))
            .andExpect(jsonPath("$.[*].hesapId").value(hasItem(DEFAULT_HESAP_ID.toString())))
            .andExpect(jsonPath("$.[*].karsiHesapId").value(hasItem(DEFAULT_KARSI_HESAP_ID.toString())));
    }

    @Test
    @Transactional
    public void getFinansalHareketDetay() throws Exception {
        // Initialize the database
        finansalHareketDetayRepository.saveAndFlush(finansalHareketDetay);

        // Get the finansalHareketDetay
        restFinansalHareketDetayMockMvc.perform(get("/api/finansal-hareket-detays/{id}", finansalHareketDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(finansalHareketDetay.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.islemTutari").value(DEFAULT_ISLEM_TUTARI.intValue()))
            .andExpect(jsonPath("$.hesapYonu").value(DEFAULT_HESAP_YONU.intValue()))
            .andExpect(jsonPath("$.hesapId").value(DEFAULT_HESAP_ID.toString()))
            .andExpect(jsonPath("$.karsiHesapId").value(DEFAULT_KARSI_HESAP_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFinansalHareketDetay() throws Exception {
        // Get the finansalHareketDetay
        restFinansalHareketDetayMockMvc.perform(get("/api/finansal-hareket-detays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinansalHareketDetay() throws Exception {
        // Initialize the database
        finansalHareketDetayService.save(finansalHareketDetay);

        int databaseSizeBeforeUpdate = finansalHareketDetayRepository.findAll().size();

        // Update the finansalHareketDetay
        FinansalHareketDetay updatedFinansalHareketDetay = finansalHareketDetayRepository.findOne(finansalHareketDetay.getId());
        // Disconnect from session so that the updates on updatedFinansalHareketDetay are not directly saved in db
        em.detach(updatedFinansalHareketDetay);
        updatedFinansalHareketDetay
            .kod(UPDATED_KOD)
            .islemTutari(UPDATED_ISLEM_TUTARI)
            .hesapYonu(UPDATED_HESAP_YONU)
            .hesapId(UPDATED_HESAP_ID)
            .karsiHesapId(UPDATED_KARSI_HESAP_ID);

        restFinansalHareketDetayMockMvc.perform(put("/api/finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinansalHareketDetay)))
            .andExpect(status().isOk());

        // Validate the FinansalHareketDetay in the database
        List<FinansalHareketDetay> finansalHareketDetayList = finansalHareketDetayRepository.findAll();
        assertThat(finansalHareketDetayList).hasSize(databaseSizeBeforeUpdate);
        FinansalHareketDetay testFinansalHareketDetay = finansalHareketDetayList.get(finansalHareketDetayList.size() - 1);
        assertThat(testFinansalHareketDetay.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testFinansalHareketDetay.getIslemTutari()).isEqualTo(UPDATED_ISLEM_TUTARI);
        assertThat(testFinansalHareketDetay.getHesapYonu()).isEqualTo(UPDATED_HESAP_YONU);
        assertThat(testFinansalHareketDetay.getHesapId()).isEqualTo(UPDATED_HESAP_ID);
        assertThat(testFinansalHareketDetay.getKarsiHesapId()).isEqualTo(UPDATED_KARSI_HESAP_ID);

        // Validate the FinansalHareketDetay in Elasticsearch
        FinansalHareketDetay finansalHareketDetayEs = finansalHareketDetaySearchRepository.findOne(testFinansalHareketDetay.getId());
        assertThat(finansalHareketDetayEs).isEqualToIgnoringGivenFields(testFinansalHareketDetay);
    }

    @Test
    @Transactional
    public void updateNonExistingFinansalHareketDetay() throws Exception {
        int databaseSizeBeforeUpdate = finansalHareketDetayRepository.findAll().size();

        // Create the FinansalHareketDetay

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFinansalHareketDetayMockMvc.perform(put("/api/finansal-hareket-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalHareketDetay)))
            .andExpect(status().isCreated());

        // Validate the FinansalHareketDetay in the database
        List<FinansalHareketDetay> finansalHareketDetayList = finansalHareketDetayRepository.findAll();
        assertThat(finansalHareketDetayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFinansalHareketDetay() throws Exception {
        // Initialize the database
        finansalHareketDetayService.save(finansalHareketDetay);

        int databaseSizeBeforeDelete = finansalHareketDetayRepository.findAll().size();

        // Get the finansalHareketDetay
        restFinansalHareketDetayMockMvc.perform(delete("/api/finansal-hareket-detays/{id}", finansalHareketDetay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean finansalHareketDetayExistsInEs = finansalHareketDetaySearchRepository.exists(finansalHareketDetay.getId());
        assertThat(finansalHareketDetayExistsInEs).isFalse();

        // Validate the database is empty
        List<FinansalHareketDetay> finansalHareketDetayList = finansalHareketDetayRepository.findAll();
        assertThat(finansalHareketDetayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFinansalHareketDetay() throws Exception {
        // Initialize the database
        finansalHareketDetayService.save(finansalHareketDetay);

        // Search the finansalHareketDetay
        restFinansalHareketDetayMockMvc.perform(get("/api/_search/finansal-hareket-detays?query=id:" + finansalHareketDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finansalHareketDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].islemTutari").value(hasItem(DEFAULT_ISLEM_TUTARI.intValue())))
            .andExpect(jsonPath("$.[*].hesapYonu").value(hasItem(DEFAULT_HESAP_YONU.intValue())))
            .andExpect(jsonPath("$.[*].hesapId").value(hasItem(DEFAULT_HESAP_ID.toString())))
            .andExpect(jsonPath("$.[*].karsiHesapId").value(hasItem(DEFAULT_KARSI_HESAP_ID.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinansalHareketDetay.class);
        FinansalHareketDetay finansalHareketDetay1 = new FinansalHareketDetay();
        finansalHareketDetay1.setId(1L);
        FinansalHareketDetay finansalHareketDetay2 = new FinansalHareketDetay();
        finansalHareketDetay2.setId(finansalHareketDetay1.getId());
        assertThat(finansalHareketDetay1).isEqualTo(finansalHareketDetay2);
        finansalHareketDetay2.setId(2L);
        assertThat(finansalHareketDetay1).isNotEqualTo(finansalHareketDetay2);
        finansalHareketDetay1.setId(null);
        assertThat(finansalHareketDetay1).isNotEqualTo(finansalHareketDetay2);
    }
}
