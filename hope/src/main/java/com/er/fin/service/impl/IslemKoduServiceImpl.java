package com.er.fin.service.impl;

import com.er.fin.service.IslemKoduService;
import com.er.fin.domain.IslemKodu;
import com.er.fin.repository.IslemKoduRepository;
import com.er.fin.repository.search.IslemKoduSearchRepository;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IslemKodu.
 */
@Service
@Transactional
public class IslemKoduServiceImpl implements IslemKoduService {

    private final Logger log = LoggerFactory.getLogger(IslemKoduServiceImpl.class);

    private final IslemKoduRepository islemKoduRepository;

    private final IslemKoduSearchRepository islemKoduSearchRepository;

    public IslemKoduServiceImpl(IslemKoduRepository islemKoduRepository, IslemKoduSearchRepository islemKoduSearchRepository) {
        this.islemKoduRepository = islemKoduRepository;
        this.islemKoduSearchRepository = islemKoduSearchRepository;
    }

    /**
     * Save a islemKodu.
     *
     * @param islemKodu the entity to save
     * @return the persisted entity
     */
    @Override
    public IslemKodu save(IslemKodu islemKodu) {
        log.debug("Request to save IslemKodu : {}", islemKodu);
        IslemKodu result = islemKoduRepository.save(islemKodu);
        islemKoduSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the islemKodus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IslemKodu> findAll(Pageable pageable) {
        log.debug("Request to get all IslemKodus");
        pageable = FinUtil.getPageParam();
        return islemKoduRepository.findAll(pageable);
    }

    /**
     * Get one islemKodu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IslemKodu findOne(Long id) {
        log.debug("Request to get IslemKodu : {}", id);
        return islemKoduRepository.findOne(id);
    }

    /**
     * Delete the islemKodu by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IslemKodu : {}", id);
        islemKoduRepository.delete(id);
        islemKoduSearchRepository.delete(id);
    }

    /**
     * Search for the islemKodu corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IslemKodu> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IslemKodus for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<IslemKodu> result = islemKoduSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
