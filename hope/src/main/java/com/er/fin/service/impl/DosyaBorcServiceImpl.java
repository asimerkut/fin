package com.er.fin.service.impl;

import com.er.fin.service.DosyaBorcService;
import com.er.fin.domain.DosyaBorc;
import com.er.fin.repository.DosyaBorcRepository;
import com.er.fin.repository.search.DosyaBorcSearchRepository;
import com.er.fin.service.dto.ComboSelDTO;
import com.er.fin.service.dto.FinUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DosyaBorc.
 */
@Service
@Transactional
public class DosyaBorcServiceImpl implements DosyaBorcService {

    private final Logger log = LoggerFactory.getLogger(DosyaBorcServiceImpl.class);

    private final DosyaBorcRepository dosyaBorcRepository;

    private final DosyaBorcRepository dosyaBorcSearchRepository;

    public DosyaBorcServiceImpl(DosyaBorcRepository dosyaBorcRepository, DosyaBorcRepository dosyaBorcSearchRepository) {
        this.dosyaBorcRepository = dosyaBorcRepository;
        this.dosyaBorcSearchRepository = dosyaBorcSearchRepository;
    }

    /**
     * Save a dosyaBorc.
     *
     * @param dosyaBorc the entity to save
     * @return the persisted entity
     */
    @Override
    public DosyaBorc save(DosyaBorc dosyaBorc) {
        log.debug("Request to save DosyaBorc : {}", dosyaBorc);
        DosyaBorc result = dosyaBorcRepository.save(dosyaBorc);
        dosyaBorcSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dosyaBorcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosyaBorc> findAll(Pageable pageable) {
        log.debug("Request to get all DosyaBorcs");
        pageable = FinUtil.getPageParam();
        return dosyaBorcRepository.findAll(pageable);
    }

    /**
     * Get one dosyaBorc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DosyaBorc findOne(Long id) {
        log.debug("Request to get DosyaBorc : {}", id);
        return dosyaBorcRepository.findOne(id);
    }

    /**
     * Delete the dosyaBorc by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DosyaBorc : {}", id);
        dosyaBorcRepository.delete(id);
        dosyaBorcSearchRepository.delete(id);
    }

    /**
     * Search for the dosyaBorc corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosyaBorc> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DosyaBorcs for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<DosyaBorc> result = null;
        ComboSelDTO obj = FinUtil.getComboSelDTO(query);
        if (obj!=null){
            result = dosyaBorcSearchRepository.findAllByDosyaId(pageable, obj.getSelId());
        } else {
            result = dosyaBorcSearchRepository.findAll(pageable);
        }
        return result;
    }
}
