package com.er.fin.service.impl;

import com.er.fin.service.HopFinansalHareketService;
import com.er.fin.domain.HopFinansalHareket;
import com.er.fin.repository.HopFinansalHareketRepository;
import com.er.fin.repository.search.HopFinansalHareketSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HopFinansalHareket.
 */
@Service
@Transactional
public class HopFinansalHareketServiceImpl implements HopFinansalHareketService {

    private final Logger log = LoggerFactory.getLogger(HopFinansalHareketServiceImpl.class);

    private final HopFinansalHareketRepository hopFinansalHareketRepository;

    private final HopFinansalHareketSearchRepository hopFinansalHareketSearchRepository;

    public HopFinansalHareketServiceImpl(HopFinansalHareketRepository hopFinansalHareketRepository, HopFinansalHareketSearchRepository hopFinansalHareketSearchRepository) {
        this.hopFinansalHareketRepository = hopFinansalHareketRepository;
        this.hopFinansalHareketSearchRepository = hopFinansalHareketSearchRepository;
    }

    /**
     * Save a hopFinansalHareket.
     *
     * @param hopFinansalHareket the entity to save
     * @return the persisted entity
     */
    @Override
    public HopFinansalHareket save(HopFinansalHareket hopFinansalHareket) {
        log.debug("Request to save HopFinansalHareket : {}", hopFinansalHareket);
        HopFinansalHareket result = hopFinansalHareketRepository.save(hopFinansalHareket);
        hopFinansalHareketSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hopFinansalHarekets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopFinansalHareket> findAll() {
        log.debug("Request to get all HopFinansalHarekets");
        return hopFinansalHareketRepository.findAll();
    }

    /**
     * Get one hopFinansalHareket by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopFinansalHareket findOne(Long id) {
        log.debug("Request to get HopFinansalHareket : {}", id);
        return hopFinansalHareketRepository.findOne(id);
    }

    /**
     * Delete the hopFinansalHareket by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopFinansalHareket : {}", id);
        hopFinansalHareketRepository.delete(id);
        hopFinansalHareketSearchRepository.delete(id);
    }

    /**
     * Search for the hopFinansalHareket corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopFinansalHareket> search(String query) {
        log.debug("Request to search HopFinansalHarekets for query {}", query);
        return StreamSupport
            .stream(hopFinansalHareketSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
