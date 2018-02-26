package com.er.fin.service.impl;

import com.er.fin.service.HopDosyaBorcService;
import com.er.fin.domain.HopDosyaBorc;
import com.er.fin.repository.HopDosyaBorcRepository;
import com.er.fin.repository.search.HopDosyaBorcSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HopDosyaBorc.
 */
@Service
@Transactional
public class HopDosyaBorcServiceImpl implements HopDosyaBorcService {

    private final Logger log = LoggerFactory.getLogger(HopDosyaBorcServiceImpl.class);

    private final HopDosyaBorcRepository hopDosyaBorcRepository;

    private final HopDosyaBorcSearchRepository hopDosyaBorcSearchRepository;

    public HopDosyaBorcServiceImpl(HopDosyaBorcRepository hopDosyaBorcRepository, HopDosyaBorcSearchRepository hopDosyaBorcSearchRepository) {
        this.hopDosyaBorcRepository = hopDosyaBorcRepository;
        this.hopDosyaBorcSearchRepository = hopDosyaBorcSearchRepository;
    }

    /**
     * Save a hopDosyaBorc.
     *
     * @param hopDosyaBorc the entity to save
     * @return the persisted entity
     */
    @Override
    public HopDosyaBorc save(HopDosyaBorc hopDosyaBorc) {
        log.debug("Request to save HopDosyaBorc : {}", hopDosyaBorc);
        HopDosyaBorc result = hopDosyaBorcRepository.save(hopDosyaBorc);
        hopDosyaBorcSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hopDosyaBorcs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDosyaBorc> findAll() {
        log.debug("Request to get all HopDosyaBorcs");
        return hopDosyaBorcRepository.findAll();
    }

    /**
     * Get one hopDosyaBorc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopDosyaBorc findOne(Long id) {
        log.debug("Request to get HopDosyaBorc : {}", id);
        return hopDosyaBorcRepository.findOne(id);
    }

    /**
     * Delete the hopDosyaBorc by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopDosyaBorc : {}", id);
        hopDosyaBorcRepository.delete(id);
        hopDosyaBorcSearchRepository.delete(id);
    }

    /**
     * Search for the hopDosyaBorc corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDosyaBorc> search(String query) {
        log.debug("Request to search HopDosyaBorcs for query {}", query);
        return StreamSupport
            .stream(hopDosyaBorcSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
