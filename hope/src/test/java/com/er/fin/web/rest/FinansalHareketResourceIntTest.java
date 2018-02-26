package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.FinansalHareket;
import com.er.fin.repository.FinansalHareketRepository;
import com.er.fin.service.FinansalHareketService;
import com.er.fin.repository.search.FinansalHareketSearchRepository;
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
 * Test class for the FinansalHareketResource REST controller.
 *
 * @see FinansalHareketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class FinansalHareketResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISLEM_KABUL_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISLEM_KABUL_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_ISLEM_TUTARI = new BigDecimal(1);
    private static final BigDecimal UPDATED_ISLEM_TUTARI = new BigDecimal(2);

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    @Autowired
    private FinansalHareketRepository finansalHareketRepository;

    @Autowired
    private FinansalHareketService finansalHareketService;

    @Autowired
    private FinansalHareketSearchRepository finansalHareketSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFinansalHareketMockMvc;

    private FinansalHareket finansalHareket;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinansalHareketResource finansalHareketResource = new FinansalHareketResource(finansalHareketService);
        this.restFinansalHareketMockMvc = MockMvcBuilders.standaloneSetup(finansalHareketResource)
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
    public static FinansalHareket createEntity(EntityManager em) {
        FinansalHareket finansalHareket = new FinansalHareket()
            .kod(DEFAULT_KOD)
            .islemKabulTarihi(DEFAULT_ISLEM_KABUL_TARIHI)
            .islemTutari(DEFAULT_ISLEM_TUTARI)
            .aciklama(DEFAULT_ACIKLAMA);
        return finansalHareket;
    }

    @Before
    public void initTest() {
        finansalHareketSearchRepository.deleteAll();
        finansalHareket = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinansalHareket() throws Exception {
        int databaseSizeBeforeCreate = finansalHareketRepository.findAll().size();

        // Create the FinansalHareket
        restFinansalHareketMockMvc.perform(post("/api/finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalHareket)))
            .andExpect(status().isCreated());

        // Validate the FinansalHareket in the database
        List<FinansalHareket> finansalHareketList = finansalHareketRepository.findAll();
        assertThat(finansalHareketList).hasSize(databaseSizeBeforeCreate + 1);
        FinansalHareket testFinansalHareket = finansalHareketList.get(finansalHareketList.size() - 1);
        assertThat(testFinansalHareket.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testFinansalHareket.getIslemKabulTarihi()).isEqualTo(DEFAULT_ISLEM_KABUL_TARIHI);
        assertThat(testFinansalHareket.getIslemTutari()).isEqualTo(DEFAULT_ISLEM_TUTARI);
        assertThat(testFinansalHareket.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);

        // Validate the FinansalHareket in Elasticsearch
        FinansalHareket finansalHareketEs = finansalHareketSearchRepository.findOne(testFinansalHareket.getId());
        assertThat(finansalHareketEs).isEqualToIgnoringGivenFields(testFinansalHareket);
    }

    @Test
    @Transactional
    public void createFinansalHareketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = finansalHareketRepository.findAll().size();

        // Create the FinansalHareket with an existing ID
        finansalHareket.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinansalHareketMockMvc.perform(post("/api/finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalHareket)))
            .andExpect(status().isBadRequest());

        // Validate the FinansalHareket in the database
        List<FinansalHareket> finansalHareketList = finansalHareketRepository.findAll();
        assertThat(finansalHareketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFinansalHarekets() throws Exception {
        // Initialize the database
        finansalHareketRepository.saveAndFlush(finansalHareket);

        // Get all the finansalHareketList
        restFinansalHareketMockMvc.perform(get("/api/finansal-harekets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finansalHareket.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].islemKabulTarihi").value(hasItem(DEFAULT_ISLEM_KABUL_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].islemTutari").value(hasItem(DEFAULT_ISLEM_TUTARI.intValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }

    @Test
    @Transactional
    public void getFinansalHareket() throws Exception {
        // Initialize the database
        finansalHareketRepository.saveAndFlush(finansalHareket);

        // Get the finansalHareket
        restFinansalHareketMockMvc.perform(get("/api/finansal-harekets/{id}", finansalHareket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(finansalHareket.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.islemKabulTarihi").value(DEFAULT_ISLEM_KABUL_TARIHI.toString()))
            .andExpect(jsonPath("$.islemTutari").value(DEFAULT_ISLEM_TUTARI.intValue()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFinansalHareket() throws Exception {
        // Get the finansalHareket
        restFinansalHareketMockMvc.perform(get("/api/finansal-harekets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinansalHareket() throws Exception {
        // Initialize the database
        finansalHareketService.save(finansalHareket);

        int databaseSizeBeforeUpdate = finansalHareketRepository.findAll().size();

        // Update the finansalHareket
        FinansalHareket updatedFinansalHareket = finansalHareketRepository.findOne(finansalHareket.getId());
        // Disconnect from session so that the updates on updatedFinansalHareket are not directly saved in db
        em.detach(updatedFinansalHareket);
        updatedFinansalHareket
            .kod(UPDATED_KOD)
            .islemKabulTarihi(UPDATED_ISLEM_KABUL_TARIHI)
            .islemTutari(UPDATED_ISLEM_TUTARI)
            .aciklama(UPDATED_ACIKLAMA);

        restFinansalHareketMockMvc.perform(put("/api/finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinansalHareket)))
            .andExpect(status().isOk());

        // Validate the FinansalHareket in the database
        List<FinansalHareket> finansalHareketList = finansalHareketRepository.findAll();
        assertThat(finansalHareketList).hasSize(databaseSizeBeforeUpdate);
        FinansalHareket testFinansalHareket = finansalHareketList.get(finansalHareketList.size() - 1);
        assertThat(testFinansalHareket.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testFinansalHareket.getIslemKabulTarihi()).isEqualTo(UPDATED_ISLEM_KABUL_TARIHI);
        assertThat(testFinansalHareket.getIslemTutari()).isEqualTo(UPDATED_ISLEM_TUTARI);
        assertThat(testFinansalHareket.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);

        // Validate the FinansalHareket in Elasticsearch
        FinansalHareket finansalHareketEs = finansalHareketSearchRepository.findOne(testFinansalHareket.getId());
        assertThat(finansalHareketEs).isEqualToIgnoringGivenFields(testFinansalHareket);
    }

    @Test
    @Transactional
    public void updateNonExistingFinansalHareket() throws Exception {
        int databaseSizeBeforeUpdate = finansalHareketRepository.findAll().size();

        // Create the FinansalHareket

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFinansalHareketMockMvc.perform(put("/api/finansal-harekets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(finansalHareket)))
            .andExpect(status().isCreated());

        // Validate the FinansalHareket in the database
        List<FinansalHareket> finansalHareketList = finansalHareketRepository.findAll();
        assertThat(finansalHareketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFinansalHareket() throws Exception {
        // Initialize the database
        finansalHareketService.save(finansalHareket);

        int databaseSizeBeforeDelete = finansalHareketRepository.findAll().size();

        // Get the finansalHareket
        restFinansalHareketMockMvc.perform(delete("/api/finansal-harekets/{id}", finansalHareket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean finansalHareketExistsInEs = finansalHareketSearchRepository.exists(finansalHareket.getId());
        assertThat(finansalHareketExistsInEs).isFalse();

        // Validate the database is empty
        List<FinansalHareket> finansalHareketList = finansalHareketRepository.findAll();
        assertThat(finansalHareketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFinansalHareket() throws Exception {
        // Initialize the database
        finansalHareketService.save(finansalHareket);

        // Search the finansalHareket
        restFinansalHareketMockMvc.perform(get("/api/_search/finansal-harekets?query=id:" + finansalHareket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finansalHareket.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].islemKabulTarihi").value(hasItem(DEFAULT_ISLEM_KABUL_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].islemTutari").value(hasItem(DEFAULT_ISLEM_TUTARI.intValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinansalHareket.class);
        FinansalHareket finansalHareket1 = new FinansalHareket();
        finansalHareket1.setId(1L);
        FinansalHareket finansalHareket2 = new FinansalHareket();
        finansalHareket2.setId(finansalHareket1.getId());
        assertThat(finansalHareket1).isEqualTo(finansalHareket2);
        finansalHareket2.setId(2L);
        assertThat(finansalHareket1).isNotEqualTo(finansalHareket2);
        finansalHareket1.setId(null);
        assertThat(finansalHareket1).isNotEqualTo(finansalHareket2);
    }
}
