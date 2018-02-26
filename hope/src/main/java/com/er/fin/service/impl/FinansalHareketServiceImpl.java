package com.er.fin.service.impl;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.service.FinansalHareketService;
import com.er.fin.domain.FinansalHareket;
import com.er.fin.repository.FinansalHareketRepository;
import com.er.fin.repository.search.FinansalHareketSearchRepository;
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
 * Service Implementation for managing FinansalHareket.
 */
@Service
@Transactional
public class FinansalHareketServiceImpl implements FinansalHareketService {

    private final Logger log = LoggerFactory.getLogger(FinansalHareketServiceImpl.class);

    private final FinansalHareketRepository finansalHareketRepository;

    private final FinansalHareketRepository finansalHareketSearchRepository;

    public FinansalHareketServiceImpl(FinansalHareketRepository finansalHareketRepository, FinansalHareketRepository finansalHareketSearchRepository) {
        this.finansalHareketRepository = finansalHareketRepository;
        this.finansalHareketSearchRepository = finansalHareketSearchRepository;
    }

    /**
     * Save a finansalHareket.
     *
     * @param finansalHareket the entity to save
     * @return the persisted entity
     */
    @Override
    public FinansalHareket save(FinansalHareket finansalHareket) {
        log.debug("Request to save FinansalHareket : {}", finansalHareket);
        FinansalHareket result = finansalHareketRepository.save(finansalHareket);
        finansalHareketSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the finansalHarekets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinansalHareket> findAll(Pageable pageable) {
        log.debug("Request to get all FinansalHarekets");
        pageable = FinUtil.getPageParam();
        return finansalHareketRepository.findAll(pageable);
    }

    /**
     * Get one finansalHareket by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FinansalHareket findOne(Long id) {
        log.debug("Request to get FinansalHareket : {}", id);
        return finansalHareketRepository.findOne(id);
    }

    /**
     * Delete the finansalHareket by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinansalHareket : {}", id);
        finansalHareketRepository.delete(id);
        finansalHareketSearchRepository.delete(id);
    }

    /**
     * Search for the finansalHareket corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinansalHareket> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FinansalHarekets for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<FinansalHareket> result = null;
        ComboSelDTO obj = FinUtil.getComboSelDTO(query);
        if (obj!=null){
            result = finansalHareketSearchRepository.findAllByDosyaId(pageable, obj.getSelId());
        } else {
            result = finansalHareketSearchRepository.findAll(pageable);
        }
        return result;
    }
}
