package com.er.fin.service.impl;

import com.er.fin.service.DosyaService;
import com.er.fin.domain.Dosya;
import com.er.fin.repository.DosyaRepository;
import com.er.fin.repository.search.DosyaSearchRepository;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Dosya.
 */
@Service
@Transactional
public class DosyaServiceImpl implements DosyaService {

    private final Logger log = LoggerFactory.getLogger(DosyaServiceImpl.class);

    private final DosyaRepository dosyaRepository;

    private final DosyaSearchRepository dosyaSearchRepository;

    public DosyaServiceImpl(DosyaRepository dosyaRepository, DosyaSearchRepository dosyaSearchRepository) {
        this.dosyaRepository = dosyaRepository;
        this.dosyaSearchRepository = dosyaSearchRepository;
    }

    /**
     * Save a dosya.
     *
     * @param dosya the entity to save
     * @return the persisted entity
     */
    @Override
    public Dosya save(Dosya dosya) {
        log.debug("Request to save Dosya : {}", dosya);
        Dosya result = dosyaRepository.save(dosya);
        dosyaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dosyas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dosya> findAll(Pageable pageable) {
        log.debug("Request to get all Dosyas");
        pageable = FinUtil.getPageParam();
        return dosyaRepository.findAll(pageable);
    }

    /**
     * Get one dosya by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Dosya findOne(Long id) {
        log.debug("Request to get Dosya : {}", id);
        return dosyaRepository.findOne(id);
    }

    /**
     * Delete the dosya by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dosya : {}", id);
        dosyaRepository.delete(id);
        dosyaSearchRepository.delete(id);
    }

    /**
     * Search for the dosya corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dosya> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dosyas for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<Dosya> result = dosyaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
