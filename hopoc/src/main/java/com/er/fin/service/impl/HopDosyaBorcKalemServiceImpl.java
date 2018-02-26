package com.er.fin.service.impl;

import com.er.fin.service.HopDosyaBorcKalemService;
import com.er.fin.domain.HopDosyaBorcKalem;
import com.er.fin.repository.HopDosyaBorcKalemRepository;
import com.er.fin.repository.search.HopDosyaBorcKalemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HopDosyaBorcKalem.
 */
@Service
@Transactional
public class HopDosyaBorcKalemServiceImpl implements HopDosyaBorcKalemService {

    private final Logger log = LoggerFactory.getLogger(HopDosyaBorcKalemServiceImpl.class);

    private final HopDosyaBorcKalemRepository hopDosyaBorcKalemRepository;

    private final HopDosyaBorcKalemSearchRepository hopDosyaBorcKalemSearchRepository;

    public HopDosyaBorcKalemServiceImpl(HopDosyaBorcKalemRepository hopDosyaBorcKalemRepository, HopDosyaBorcKalemSearchRepository hopDosyaBorcKalemSearchRepository) {
        this.hopDosyaBorcKalemRepository = hopDosyaBorcKalemRepository;
        this.hopDosyaBorcKalemSearchRepository = hopDosyaBorcKalemSearchRepository;
    }

    /**
     * Save a hopDosyaBorcKalem.
     *
     * @param hopDosyaBorcKalem the entity to save
     * @return the persisted entity
     */
    @Override
    public HopDosyaBorcKalem save(HopDosyaBorcKalem hopDosyaBorcKalem) {
        log.debug("Request to save HopDosyaBorcKalem : {}", hopDosyaBorcKalem);
        HopDosyaBorcKalem result = hopDosyaBorcKalemRepository.save(hopDosyaBorcKalem);
        hopDosyaBorcKalemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hopDosyaBorcKalems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDosyaBorcKalem> findAll() {
        log.debug("Request to get all HopDosyaBorcKalems");
        return hopDosyaBorcKalemRepository.findAll();
    }

    /**
     * Get one hopDosyaBorcKalem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopDosyaBorcKalem findOne(Long id) {
        log.debug("Request to get HopDosyaBorcKalem : {}", id);
        return hopDosyaBorcKalemRepository.findOne(id);
    }

    /**
     * Delete the hopDosyaBorcKalem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopDosyaBorcKalem : {}", id);
        hopDosyaBorcKalemRepository.delete(id);
        hopDosyaBorcKalemSearchRepository.delete(id);
    }

    /**
     * Search for the hopDosyaBorcKalem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDosyaBorcKalem> search(String query) {
        log.debug("Request to search HopDosyaBorcKalems for query {}", query);
        return StreamSupport
            .stream(hopDosyaBorcKalemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
