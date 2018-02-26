package com.er.fin.service.impl;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.service.FinansalHareketDetayService;
import com.er.fin.domain.FinansalHareketDetay;
import com.er.fin.repository.FinansalHareketDetayRepository;
import com.er.fin.repository.search.FinansalHareketDetaySearchRepository;
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
 * Service Implementation for managing FinansalHareketDetay.
 */
@Service
@Transactional
public class FinansalHareketDetayServiceImpl implements FinansalHareketDetayService {

    private final Logger log = LoggerFactory.getLogger(FinansalHareketDetayServiceImpl.class);

    private final FinansalHareketDetayRepository finansalHareketDetayRepository;

    private final FinansalHareketDetayRepository finansalHareketDetaySearchRepository;

    public FinansalHareketDetayServiceImpl(FinansalHareketDetayRepository finansalHareketDetayRepository, FinansalHareketDetayRepository finansalHareketDetaySearchRepository) {
        this.finansalHareketDetayRepository = finansalHareketDetayRepository;
        this.finansalHareketDetaySearchRepository = finansalHareketDetaySearchRepository;
    }

    /**
     * Save a finansalHareketDetay.
     *
     * @param finansalHareketDetay the entity to save
     * @return the persisted entity
     */
    @Override
    public FinansalHareketDetay save(FinansalHareketDetay finansalHareketDetay) {
        log.debug("Request to save FinansalHareketDetay : {}", finansalHareketDetay);
        FinansalHareketDetay result = finansalHareketDetayRepository.save(finansalHareketDetay);
        finansalHareketDetaySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the finansalHareketDetays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinansalHareketDetay> findAll(Pageable pageable) {
        log.debug("Request to get all FinansalHareketDetays");
        pageable = FinUtil.getPageParam();
        return finansalHareketDetayRepository.findAll(pageable);
    }

    /**
     * Get one finansalHareketDetay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FinansalHareketDetay findOne(Long id) {
        log.debug("Request to get FinansalHareketDetay : {}", id);
        return finansalHareketDetayRepository.findOne(id);
    }

    /**
     * Delete the finansalHareketDetay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinansalHareketDetay : {}", id);
        finansalHareketDetayRepository.delete(id);
        finansalHareketDetaySearchRepository.delete(id);
    }

    /**
     * Search for the finansalHareketDetay corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinansalHareketDetay> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FinansalHareketDetays for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<FinansalHareketDetay> result = null;
        ComboSelDTO obj = FinUtil.getComboSelDTO(query);
        if (obj!=null){
            result = finansalHareketDetaySearchRepository.findAllByFinansalHareketDosyaId(pageable, obj.getSelId());
        } else {
            result = finansalHareketDetaySearchRepository.findAll(pageable);
        }
        return result;
    }
}
