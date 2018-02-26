package com.er.fin.service.impl;

import com.er.fin.service.BorcGrubuService;
import com.er.fin.domain.BorcGrubu;
import com.er.fin.repository.BorcGrubuRepository;
import com.er.fin.repository.search.BorcGrubuSearchRepository;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BorcGrubu.
 */
@Service
@Transactional
public class BorcGrubuServiceImpl implements BorcGrubuService {

    private final Logger log = LoggerFactory.getLogger(BorcGrubuServiceImpl.class);

    private final BorcGrubuRepository borcGrubuRepository;

    private final BorcGrubuSearchRepository borcGrubuSearchRepository;

    public BorcGrubuServiceImpl(BorcGrubuRepository borcGrubuRepository, BorcGrubuSearchRepository borcGrubuSearchRepository) {
        this.borcGrubuRepository = borcGrubuRepository;
        this.borcGrubuSearchRepository = borcGrubuSearchRepository;
    }

    /**
     * Save a borcGrubu.
     *
     * @param borcGrubu the entity to save
     * @return the persisted entity
     */
    @Override
    public BorcGrubu save(BorcGrubu borcGrubu) {
        log.debug("Request to save BorcGrubu : {}", borcGrubu);
        BorcGrubu result = borcGrubuRepository.save(borcGrubu);
        borcGrubuSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the borcGrubus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BorcGrubu> findAll(Pageable pageable) {
        log.debug("Request to get all BorcGrubus");
        pageable = FinUtil.getPageParam();
        return borcGrubuRepository.findAll(pageable);
    }

    /**
     * Get one borcGrubu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BorcGrubu findOne(Long id) {
        log.debug("Request to get BorcGrubu : {}", id);
        return borcGrubuRepository.findOne(id);
    }

    /**
     * Delete the borcGrubu by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BorcGrubu : {}", id);
        borcGrubuRepository.delete(id);
        borcGrubuSearchRepository.delete(id);
    }

    /**
     * Search for the borcGrubu corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BorcGrubu> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BorcGrubus for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<BorcGrubu> result = borcGrubuSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
