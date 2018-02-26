package com.er.fin.service.impl;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.service.BorcService;
import com.er.fin.domain.Borc;
import com.er.fin.repository.BorcRepository;
import com.er.fin.repository.search.BorcSearchRepository;
import com.er.fin.service.dto.ComboSelDTO;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Borc.
 */
@Service
@Transactional
public class BorcServiceImpl implements BorcService {

    private final Logger log = LoggerFactory.getLogger(BorcServiceImpl.class);

    private final BorcRepository borcRepository;

    private final BorcRepository borcSearchRepository;

    public BorcServiceImpl(BorcRepository borcRepository, BorcRepository borcSearchRepository) {
        this.borcRepository = borcRepository;
        this.borcSearchRepository = borcSearchRepository;
    }

    /**
     * Save a borc.
     *
     * @param borc the entity to save
     * @return the persisted entity
     */
    @Override
    public Borc save(Borc borc) {
        log.debug("Request to save Borc : {}", borc);
        Borc result = borcRepository.save(borc);
        borcSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the borcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Borc> findAll(Pageable pageable) {
        log.debug("Request to get all Borcs");
        pageable = FinUtil.getPageParam();
        return borcRepository.findAll(pageable);
    }

    /**
     * Get one borc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Borc findOne(Long id) {
        log.debug("Request to get Borc : {}", id);
        return borcRepository.findOne(id);
    }

    /**
     * Delete the borc by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Borc : {}", id);
        borcRepository.delete(id);
        borcSearchRepository.delete(id);
    }

    /**
     * Search for the borc corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Borc> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Borcs for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<Borc> result = null;
        ComboSelDTO obj = FinUtil.getComboSelDTO(query);
        if (obj!=null){
            result = borcSearchRepository.findAllByDosyaId(pageable, obj.getSelId());
        } else {
            result = borcSearchRepository.findAll(pageable);
        }
        return result;
    }
}
