package com.er.fin.web.rest;

import com.er.fin.FinApp;

import com.er.fin.domain.HopDosyaBorc;
import com.er.fin.repository.HopDosyaBorcRepository;
import com.er.fin.service.HopDosyaBorcService;
import com.er.fin.repository.search.HopDosyaBorcSearchRepository;
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

import com.er.fin.domain.enumeration.EnmBorcGrubu;
/**
 * Test class for the HopDosyaBorcResource REST controller.
 *
 * @see HopDosyaBorcResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinApp.class)
public class HopDosyaBorcResourceIntTest {

    private static final String DEFAULT_KOD = "AAAAAAAAAA";
    private static final String UPDATED_KOD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TUTAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_TUTAR = new BigDecimal(2);

    private static final EnmBorcGrubu DEFAULT_BORC_GRUBU = EnmBorcGrubu.BG_00;
    private static final EnmBorcGrubu UPDATED_BORC_GRUBU = EnmBorcGrubu.BG_01;

    @Autowired
    private HopDosyaBorcRepository hopDosyaBorcRepository;

    @Autowired
    private HopDosyaBorcService hopDosyaBorcService;

    @Autowired
    private HopDosyaBorcSearchRepository hopDosyaBorcSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHopDosyaBorcMockMvc;

    private HopDosyaBorc hopDosyaBorc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HopDosyaBorcResource hopDosyaBorcResource = new HopDosyaBorcResource(hopDosyaBorcService);
        this.restHopDosyaBorcMockMvc = MockMvcBuilders.standaloneSetup(hopDosyaBorcResource)
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
    public static HopDosyaBorc createEntity(EntityManager em) {
        HopDosyaBorc hopDosyaBorc = new HopDosyaBorc()
            .kod(DEFAULT_KOD)
            .tutar(DEFAULT_TUTAR)
            .borcGrubu(DEFAULT_BORC_GRUBU);
        return hopDosyaBorc;
    }

    @Before
    public void initTest() {
        hopDosyaBorcSearchRepository.deleteAll();
        hopDosyaBorc = createEntity(em);
    }

    @Test
    @Transactional
    public void createHopDosyaBorc() throws Exception {
        int databaseSizeBeforeCreate = hopDosyaBorcRepository.findAll().size();

        // Create the HopDosyaBorc
        restHopDosyaBorcMockMvc.perform(post("/api/hop-dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosyaBorc)))
            .andExpect(status().isCreated());

        // Validate the HopDosyaBorc in the database
        List<HopDosyaBorc> hopDosyaBorcList = hopDosyaBorcRepository.findAll();
        assertThat(hopDosyaBorcList).hasSize(databaseSizeBeforeCreate + 1);
        HopDosyaBorc testHopDosyaBorc = hopDosyaBorcList.get(hopDosyaBorcList.size() - 1);
        assertThat(testHopDosyaBorc.getKod()).isEqualTo(DEFAULT_KOD);
        assertThat(testHopDosyaBorc.getTutar()).isEqualTo(DEFAULT_TUTAR);
        assertThat(testHopDosyaBorc.getBorcGrubu()).isEqualTo(DEFAULT_BORC_GRUBU);

        // Validate the HopDosyaBorc in Elasticsearch
        HopDosyaBorc hopDosyaBorcEs = hopDosyaBorcSearchRepository.findOne(testHopDosyaBorc.getId());
        assertThat(hopDosyaBorcEs).isEqualToIgnoringGivenFields(testHopDosyaBorc);
    }

    @Test
    @Transactional
    public void createHopDosyaBorcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hopDosyaBorcRepository.findAll().size();

        // Create the HopDosyaBorc with an existing ID
        hopDosyaBorc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHopDosyaBorcMockMvc.perform(post("/api/hop-dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosyaBorc)))
            .andExpect(status().isBadRequest());

        // Validate the HopDosyaBorc in the database
        List<HopDosyaBorc> hopDosyaBorcList = hopDosyaBorcRepository.findAll();
        assertThat(hopDosyaBorcList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHopDosyaBorcs() throws Exception {
        // Initialize the database
        hopDosyaBorcRepository.saveAndFlush(hopDosyaBorc);

        // Get all the hopDosyaBorcList
        restHopDosyaBorcMockMvc.perform(get("/api/hop-dosya-borcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopDosyaBorc.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].borcGrubu").value(hasItem(DEFAULT_BORC_GRUBU.toString())));
    }

    @Test
    @Transactional
    public void getHopDosyaBorc() throws Exception {
        // Initialize the database
        hopDosyaBorcRepository.saveAndFlush(hopDosyaBorc);

        // Get the hopDosyaBorc
        restHopDosyaBorcMockMvc.perform(get("/api/hop-dosya-borcs/{id}", hopDosyaBorc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hopDosyaBorc.getId().intValue()))
            .andExpect(jsonPath("$.kod").value(DEFAULT_KOD.toString()))
            .andExpect(jsonPath("$.tutar").value(DEFAULT_TUTAR.intValue()))
            .andExpect(jsonPath("$.borcGrubu").value(DEFAULT_BORC_GRUBU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHopDosyaBorc() throws Exception {
        // Get the hopDosyaBorc
        restHopDosyaBorcMockMvc.perform(get("/api/hop-dosya-borcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHopDosyaBorc() throws Exception {
        // Initialize the database
        hopDosyaBorcService.save(hopDosyaBorc);

        int databaseSizeBeforeUpdate = hopDosyaBorcRepository.findAll().size();

        // Update the hopDosyaBorc
        HopDosyaBorc updatedHopDosyaBorc = hopDosyaBorcRepository.findOne(hopDosyaBorc.getId());
        // Disconnect from session so that the updates on updatedHopDosyaBorc are not directly saved in db
        em.detach(updatedHopDosyaBorc);
        updatedHopDosyaBorc
            .kod(UPDATED_KOD)
            .tutar(UPDATED_TUTAR)
            .borcGrubu(UPDATED_BORC_GRUBU);

        restHopDosyaBorcMockMvc.perform(put("/api/hop-dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHopDosyaBorc)))
            .andExpect(status().isOk());

        // Validate the HopDosyaBorc in the database
        List<HopDosyaBorc> hopDosyaBorcList = hopDosyaBorcRepository.findAll();
        assertThat(hopDosyaBorcList).hasSize(databaseSizeBeforeUpdate);
        HopDosyaBorc testHopDosyaBorc = hopDosyaBorcList.get(hopDosyaBorcList.size() - 1);
        assertThat(testHopDosyaBorc.getKod()).isEqualTo(UPDATED_KOD);
        assertThat(testHopDosyaBorc.getTutar()).isEqualTo(UPDATED_TUTAR);
        assertThat(testHopDosyaBorc.getBorcGrubu()).isEqualTo(UPDATED_BORC_GRUBU);

        // Validate the HopDosyaBorc in Elasticsearch
        HopDosyaBorc hopDosyaBorcEs = hopDosyaBorcSearchRepository.findOne(testHopDosyaBorc.getId());
        assertThat(hopDosyaBorcEs).isEqualToIgnoringGivenFields(testHopDosyaBorc);
    }

    @Test
    @Transactional
    public void updateNonExistingHopDosyaBorc() throws Exception {
        int databaseSizeBeforeUpdate = hopDosyaBorcRepository.findAll().size();

        // Create the HopDosyaBorc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHopDosyaBorcMockMvc.perform(put("/api/hop-dosya-borcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDosyaBorc)))
            .andExpect(status().isCreated());

        // Validate the HopDosyaBorc in the database
        List<HopDosyaBorc> hopDosyaBorcList = hopDosyaBorcRepository.findAll();
        assertThat(hopDosyaBorcList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHopDosyaBorc() throws Exception {
        // Initialize the database
        hopDosyaBorcService.save(hopDosyaBorc);

        int databaseSizeBeforeDelete = hopDosyaBorcRepository.findAll().size();

        // Get the hopDosyaBorc
        restHopDosyaBorcMockMvc.perform(delete("/api/hop-dosya-borcs/{id}", hopDosyaBorc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hopDosyaBorcExistsInEs = hopDosyaBorcSearchRepository.exists(hopDosyaBorc.getId());
        assertThat(hopDosyaBorcExistsInEs).isFalse();

        // Validate the database is empty
        List<HopDosyaBorc> hopDosyaBorcList = hopDosyaBorcRepository.findAll();
        assertThat(hopDosyaBorcList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHopDosyaBorc() throws Exception {
        // Initialize the database
        hopDosyaBorcService.save(hopDosyaBorc);

        // Search the hopDosyaBorc
        restHopDosyaBorcMockMvc.perform(get("/api/_search/hop-dosya-borcs?query=id:" + hopDosyaBorc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopDosyaBorc.getId().intValue())))
            .andExpect(jsonPath("$.[*].kod").value(hasItem(DEFAULT_KOD.toString())))
            .andExpect(jsonPath("$.[*].tutar").value(hasItem(DEFAULT_TUTAR.intValue())))
            .andExpect(jsonPath("$.[*].borcGrubu").value(hasItem(DEFAULT_BORC_GRUBU.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopDosyaBorc.class);
        HopDosyaBorc hopDosyaBorc1 = new HopDosyaBorc();
        hopDosyaBorc1.setId(1L);
        HopDosyaBorc hopDosyaBorc2 = new HopDosyaBorc();
        hopDosyaBorc2.setId(hopDosyaBorc1.getId());
        assertThat(hopDosyaBorc1).isEqualTo(hopDosyaBorc2);
        hopDosyaBorc2.setId(2L);
        assertThat(hopDosyaBorc1).isNotEqualTo(hopDosyaBorc2);
        hopDosyaBorc1.setId(null);
        assertThat(hopDosyaBorc1).isNotEqualTo(hopDosyaBorc2);
    }
}
