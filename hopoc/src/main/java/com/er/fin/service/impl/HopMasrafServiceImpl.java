package com.er.fin.service.impl;

import com.er.fin.service.HopMasrafService;
import com.er.fin.domain.HopMasraf;
import com.er.fin.repository.HopMasrafRepository;
import com.er.fin.repository.search.HopMasrafSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HopMasraf.
 */
@Service
@Transactional
public class HopMasrafServiceImpl implements HopMasrafService {

    private final Logger log = LoggerFactory.getLogger(HopMasrafServiceImpl.class);

    private final HopMasrafRepository hopMasrafRepository;

    private final HopMasrafSearchRepository hopMasrafSearchRepository;

    public HopMasrafServiceImpl(HopMasrafRepository hopMasrafRepository, HopMasrafSearchRepository hopMasrafSearchRepository) {
        this.hopMasrafRepository = hopMasrafRepository;
        this.hopMasrafSearchRepository = hopMasrafSearchRepository;
    }

    /**
     * Save a hopMasraf.
     *
     * @param hopMasraf the entity to save
     * @return the persisted entity
     */
    @Override
    public HopMasraf save(HopMasraf hopMasraf) {
        log.debug("Request to save HopMasraf : {}", hopMasraf);
        HopMasraf result = hopMasrafRepository.save(hopMasraf);
        hopMasrafSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hopMasrafs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopMasraf> findAll() {
        log.debug("Request to get all HopMasrafs");
        return hopMasrafRepository.findAll();
    }

    /**
     * Get one hopMasraf by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopMasraf findOne(Long id) {
        log.debug("Request to get HopMasraf : {}", id);
        return hopMasrafRepository.findOne(id);
    }

    /**
     * Delete the hopMasraf by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopMasraf : {}", id);
        hopMasrafRepository.delete(id);
        hopMasrafSearchRepository.delete(id);
    }

    /**
     * Search for the hopMasraf corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopMasraf> search(String query) {
        log.debug("Request to search HopMasrafs for query {}", query);
        return StreamSupport
            .stream(hopMasrafSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
