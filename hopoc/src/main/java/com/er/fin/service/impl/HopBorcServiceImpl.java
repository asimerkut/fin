package com.er.fin.service.impl;

import com.er.fin.service.HopBorcService;
import com.er.fin.domain.HopBorc;
import com.er.fin.repository.HopBorcRepository;
import com.er.fin.repository.search.HopBorcSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HopBorc.
 */
@Service
@Transactional
public class HopBorcServiceImpl implements HopBorcService {

    private final Logger log = LoggerFactory.getLogger(HopBorcServiceImpl.class);

    private final HopBorcRepository hopBorcRepository;

    private final HopBorcSearchRepository hopBorcSearchRepository;

    public HopBorcServiceImpl(HopBorcRepository hopBorcRepository, HopBorcSearchRepository hopBorcSearchRepository) {
        this.hopBorcRepository = hopBorcRepository;
        this.hopBorcSearchRepository = hopBorcSearchRepository;
    }

    /**
     * Save a hopBorc.
     *
     * @param hopBorc the entity to save
     * @return the persisted entity
     */
    @Override
    public HopBorc save(HopBorc hopBorc) {
        log.debug("Request to save HopBorc : {}", hopBorc);
        HopBorc result = hopBorcRepository.save(hopBorc);
        hopBorcSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hopBorcs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopBorc> findAll() {
        log.debug("Request to get all HopBorcs");
        return hopBorcRepository.findAll();
    }

    /**
     * Get one hopBorc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopBorc findOne(Long id) {
        log.debug("Request to get HopBorc : {}", id);
        return hopBorcRepository.findOne(id);
    }

    /**
     * Delete the hopBorc by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopBorc : {}", id);
        hopBorcRepository.delete(id);
        hopBorcSearchRepository.delete(id);
    }

    /**
     * Search for the hopBorc corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopBorc> search(String query) {
        log.debug("Request to search HopBorcs for query {}", query);
        return StreamSupport
            .stream(hopBorcSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
