package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.HopFinansalHareket;
import com.er.fin.repository.HopFinansalHareketRepository;
import com.er.fin.service.HopFinansalHareketService;
import com.er.fin.repository.search.HopFinansalHareketSearchRepository;
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

import com.er.fin.domain.enumeration.EnmIslemKodu;
/**
 * Test class for the HopFinansalHareketResource REST controller.
 *
 * @see HopFinansalHareketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class HopFinansalHareketResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final EnmIslemKodu DEFAULT_ISLEM_KODU = EnmIslemKodu.ISLM_100;
    private static final EnmIslemKodu UPDATED_ISLEM_KODU = EnmIslemKodu.ISLM_101;

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_TUTAR = new BigDecimal(2);

    @Autowired
    private HopFinansalHareketRepository hopFinansalHareketRepository;

    @Autowired
    private HopFinansalHareketService hopFinansalHareketService;

    @Autowired
    private HopFinansalHareketSearchRepository hopFinansalHareketSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHopFinansalHareketMockMvc;

    private HopFinansalHareket hopFinansalHareket;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HopFinansalHareketResource hopFinansalHareketResource = new HopFinansalHareketResource(hopFinansalHareketService);
        this.restHopFinansalHareketMockMvc = MockMvcBuilders.standaloneSetup(hopFinansalHareketResource)
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
    public static HopFinansalHareket createEntity(EntityManager em) {
        HopFinansalHareket hopFinansalHareket = new HopFinansalHareket()
            .kod(DEFAULT_KOD)
            .islemKodu(DEFAULT_ISLEM_KODU)
            .tarih(DEFAULT_TARIH)
            .tutar(DEFAULT_TUTAR);
        return hopFinansalHareket;
    }

    @Before
    public void initTest() {
        hopFinansalHareketSearchRepository.deleteAll();
        hopFinansalHareket = createEntity(em);
    }

    @Test
    @Transactional
    public void createHopFinansalHareket() throws Exception {
        int databaseSizeBeforeCreate = hopFinansalHareketRepository.findAll().size();

        // Create the HopFinansalHareket
        restHopFinansalHareketMockMvc.perform(post("/api/hop-finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopFinansalHareket)))
            .andExpect(status().isCreated());

        // Validate the HopFinansalHareket in the database
        List<HopFinansalHareket> hopFinansalHareketList = hopFinansalHareketRepository.findAll();
        assertThat(hopFinansalHareketList).hasSize(databaseSizeBeforeCreate + 1);
        HopFinansalHareket testHopFinansalHareket = hopFinansalHareketList.get(hopFinansalHareketList.size() - 1);
        assertThat(testHopFinansalHareket.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testHopFinansalHareket.getIslemKodu()).isEqualTo(DEFAULT_ISLEM_KODU);
        assertThat(testHopFinansalHareket.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testHopFinansalHareket.getTutar()).isEqualTo(DEFAULT_TUTAR);

        // Validate the HopFinansalHareket in Elasticsearch
        HopFinansalHareket hopFinansalHareketEs = hopFinansalHareketSearchRepository.findOne(testHopFinansalHareket.getId());
        assertThat(hopFinansalHareketEs).isEqualToIgnoringGivenFields(testHopFinansalHareket);
    }

    @Test
    @Transactional
    public void createHopFinansalHareketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hopFinansalHareketRepository.findAll().size();

        // Create the HopFinansalHareket with an existing ID
        hopFinansalHareket.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHopFinansalHareketMockMvc.perform(post("/api/hop-finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopFinansalHareket)))
            .andExpect(status().isBadRequest());

        // Validate the HopFinansalHareket in the database
        List<HopFinansalHareket> hopFinansalHareketList = hopFinansalHareketRepository.findAll();
        assertThat(hopFinansalHareketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHopFinansalHarekets() throws Exception {
        // Initialize the database
        hopFinansalHareketRepository.saveAndFlush(hopFinansalHareket);

        // Get all the hopFinansalHareketList
        restHopFinansalHareketMockMvc.perform(get("/api/hop-finansal-harekets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopFinansalHareket.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].islemKodu").value(hasItem(DEFAULT_ISLEM_KODU.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())));
    }

    @Test
    @Transactional
    public void getHopFinansalHareket() throws Exception {
        // Initialize the database
        hopFinansalHareketRepository.saveAndFlush(hopFinansalHareket);

        // Get the hopFinansalHareket
        restHopFinansalHareketMockMvc.perform(get("/api/hop-finansal-harekets/{id}", hopFinansalHareket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hopFinansalHareket.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.islemKodu").value(DEFAULT_ISLEM_KODU.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.tutar").value(DEFAULT_TUTAR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHopFinansalHareket() throws Exception {
        // Get the hopFinansalHareket
        restHopFinansalHareketMockMvc.perform(get("/api/hop-finansal-harekets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHopFinansalHareket() throws Exception {
        // Initialize the database
        hopFinansalHareketService.save(hopFinansalHareket);

        int databaseSizeBeforeUpdate = hopFinansalHareketRepository.findAll().size();

        // Update the hopFinansalHareket
        HopFinansalHareket updatedHopFinansalHareket = hopFinansalHareketRepository.findOne(hopFinansalHareket.getId());
        // Disconnect from session so that the updates on updatedHopFinansalHareket are not directly saved in db
        em.detach(updatedHopFinansalHareket);
        updatedHopFinansalHareket
            .kod(UPDATED_KOD)
            .islemKodu(UPDATED_ISLEM_KODU)
            .tarih(UPDATED_TARIH)
            .tutar(UPDATED_TUTAR);

        restHopFinansalHareketMockMvc.perform(put("/api/hop-finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHopFinansalHareket)))
            .andExpect(status().isOk());

        // Validate the HopFinansalHareket in the database
        List<HopFinansalHareket> hopFinansalHareketList = hopFinansalHareketRepository.findAll();
        assertThat(hopFinansalHareketList).hasSize(databaseSizeBeforeUpdate);
        HopFinansalHareket testHopFinansalHareket = hopFinansalHareketList.get(hopFinansalHareketList.size() - 1);
        assertThat(testHopFinansalHareket.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testHopFinansalHareket.getIslemKodu()).isEqualTo(UPDATED_ISLEM_KODU);
        assertThat(testHopFinansalHareket.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testHopFinansalHareket.getTutar()).isEqualTo(UPDATED_TUTAR);

        // Validate the HopFinansalHareket in Elasticsearch
        HopFinansalHareket hopFinansalHareketEs = hopFinansalHareketSearchRepository.findOne(testHopFinansalHareket.getId());
        assertThat(hopFinansalHareketEs).isEqualToIgnoringGivenFields(testHopFinansalHareket);
    }

    @Test
    @Transactional
    public void updateNonExistingHopFinansalHareket() throws Exception {
        int databaseSizeBeforeUpdate = hopFinansalHareketRepository.findAll().size();

        // Create the HopFinansalHareket

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHopFinansalHareketMockMvc.perform(put("/api/hop-finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopFinansalHareket)))
            .andExpect(status().isCreated());

        // Validate the HopFinansalHareket in the database
        List<HopFinansalHareket> hopFinansalHareketList = hopFinansalHareketRepository.findAll();
        assertThat(hopFinansalHareketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHopFinansalHareket() throws Exception {
        // Initialize the database
        hopFinansalHareketService.save(hopFinansalHareket);

        int databaseSizeBeforeDelete = hopFinansalHareketRepository.findAll().size();

        // Get the hopFinansalHareket
        restHopFinansalHareketMockMvc.perform(delete("/api/hop-finansal-harekets/{id}", hopFinansalHareket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hopFinansalHareketExistsInEs = hopFinansalHareketSearchRepository.exists(hopFinansalHareket.getId());
        assertThat(hopFinansalHareketExistsInEs).isFalse();

        // Validate the database is empty
        List<HopFinansalHareket> hopFinansalHareketList = hopFinansalHareketRepository.findAll();
        assertThat(hopFinansalHareketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHopFinansalHareket() throws Exception {
        // Initialize the database
        hopFinansalHareketService.save(hopFinansalHareket);

        // Search the hopFinansalHareket
        restHopFinansalHareketMockMvc.perform(get("/api/_search/hop-finansal-harekets?query=id:" + hopFinansalHareket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopFinansalHareket.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].islemKodu").value(hasItem(DEFAULT_ISLEM_KODU.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopFinansalHareket.class);
        HopFinansalHareket hopFinansalHareket1 = new HopFinansalHareket();
        hopFinansalHareket1.setId(1L);
        HopFinansalHareket hopFinansalHareket2 = new HopFinansalHareket();
        hopFinansalHareket2.setId(hopFinansalHareket1.getId());
        assertThat(hopFinansalHareket1).isEqualTo(hopFinansalHareket2);
        hopFinansalHareket2.setId(2L);
        assertThat(hopFinansalHareket1).isNotEqualTo(hopFinansalHareket2);
        hopFinansalHareket1.setId(null);
        assertThat(hopFinansalHareket1).isNotEqualTo(hopFinansalHareket2);
    }
}
