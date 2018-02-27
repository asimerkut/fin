package com.er.fin.service.impl;

import com.er.fin.service.PerSubmitService;
import com.er.fin.domain.PerSubmit;
import com.er.fin.repository.PerSubmitRepository;
import com.er.fin.repository.search.PerSubmitSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PerSubmit.
 */
@Service
@Transactional
public class PerSubmitServiceImpl implements PerSubmitService {

    private final Logger log = LoggerFactory.getLogger(PerSubmitServiceImpl.class);

    private final PerSubmitRepository perSubmitRepository;

    private final PerSubmitSearchRepository perSubmitSearchRepository;

    public PerSubmitServiceImpl(PerSubmitRepository perSubmitRepository, PerSubmitSearchRepository perSubmitSearchRepository) {
        this.perSubmitRepository = perSubmitRepository;
        this.perSubmitSearchRepository = perSubmitSearchRepository;
    }

    /**
     * Save a perSubmit.
     *
     * @param perSubmit the entity to save
     * @return the persisted entity
     */
    @Override
    public PerSubmit save(PerSubmit perSubmit) {
        log.debug("Request to save PerSubmit : {}", perSubmit);
        PerSubmit result = perSubmitRepository.save(perSubmit);
        perSubmitSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the perSubmits.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PerSubmit> findAll() {
        log.debug("Request to get all PerSubmits");
        return perSubmitRepository.findAll();
    }

    /**
     * Get one perSubmit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PerSubmit findOne(Long id) {
        log.debug("Request to get PerSubmit : {}", id);
        return perSubmitRepository.findOne(id);
    }

    /**
     * Delete the perSubmit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PerSubmit : {}", id);
        perSubmitRepository.delete(id);
        perSubmitSearchRepository.delete(id);
    }

    /**
     * Search for the perSubmit corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PerSubmit> search(String query) {
        log.debug("Request to search PerSubmits for query {}", query);
        return StreamSupport
            .stream(perSubmitSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
