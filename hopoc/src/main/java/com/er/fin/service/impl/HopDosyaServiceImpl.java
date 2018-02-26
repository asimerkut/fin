package com.er.fin.service.impl;

import com.er.fin.service.HopDosyaService;
import com.er.fin.domain.HopDosya;
import com.er.fin.repository.HopDosyaRepository;
import com.er.fin.repository.search.HopDosyaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HopDosya.
 */
@Service
@Transactional
public class HopDosyaServiceImpl implements HopDosyaService {

    private final Logger log = LoggerFactory.getLogger(HopDosyaServiceImpl.class);

    private final HopDosyaRepository hopDosyaRepository;

    private final HopDosyaSearchRepository hopDosyaSearchRepository;

    public HopDosyaServiceImpl(HopDosyaRepository hopDosyaRepository, HopDosyaSearchRepository hopDosyaSearchRepository) {
        this.hopDosyaRepository = hopDosyaRepository;
        this.hopDosyaSearchRepository = hopDosyaSearchRepository;
    }

    /**
     * Save a hopDosya.
     *
     * @param hopDosya the entity to save
     * @return the persisted entity
     */
    @Override
    public HopDosya save(HopDosya hopDosya) {
        log.debug("Request to save HopDosya : {}", hopDosya);
        HopDosya result = hopDosyaRepository.save(hopDosya);
        hopDosyaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hopDosyas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDosya> findAll() {
        log.debug("Request to get all HopDosyas");
        return hopDosyaRepository.findAll();
    }

    /**
     * Get one hopDosya by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopDosya findOne(Long id) {
        log.debug("Request to get HopDosya : {}", id);
        return hopDosyaRepository.findOne(id);
    }

    /**
     * Delete the hopDosya by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopDosya : {}", id);
        hopDosyaRepository.delete(id);
        hopDosyaSearchRepository.delete(id);
    }

    /**
     * Search for the hopDosya corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDosya> search(String query) {
        log.debug("Request to search HopDosyas for query {}", query);
        return StreamSupport
            .stream(hopDosyaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
