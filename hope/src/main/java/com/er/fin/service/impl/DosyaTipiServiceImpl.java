package com.er.fin.service.impl;

import com.er.fin.service.DosyaTipiService;
import com.er.fin.domain.DosyaTipi;
import com.er.fin.repository.DosyaTipiRepository;
import com.er.fin.repository.search.DosyaTipiSearchRepository;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DosyaTipi.
 */
@Service
@Transactional
public class DosyaTipiServiceImpl implements DosyaTipiService {

    private final Logger log = LoggerFactory.getLogger(DosyaTipiServiceImpl.class);

    private final DosyaTipiRepository dosyaTipiRepository;

    private final DosyaTipiSearchRepository dosyaTipiSearchRepository;

    public DosyaTipiServiceImpl(DosyaTipiRepository dosyaTipiRepository, DosyaTipiSearchRepository dosyaTipiSearchRepository) {
        this.dosyaTipiRepository = dosyaTipiRepository;
        this.dosyaTipiSearchRepository = dosyaTipiSearchRepository;
    }

    /**
     * Save a dosyaTipi.
     *
     * @param dosyaTipi the entity to save
     * @return the persisted entity
     */
    @Override
    public DosyaTipi save(DosyaTipi dosyaTipi) {
        log.debug("Request to save DosyaTipi : {}", dosyaTipi);
        DosyaTipi result = dosyaTipiRepository.save(dosyaTipi);
        dosyaTipiSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dosyaTipis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosyaTipi> findAll(Pageable pageable) {
        log.debug("Request to get all DosyaTipis");
        pageable = FinUtil.getPageParam();
        return dosyaTipiRepository.findAll(pageable);
    }

    /**
     * Get one dosyaTipi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DosyaTipi findOne(Long id) {
        log.debug("Request to get DosyaTipi : {}", id);
        return dosyaTipiRepository.findOne(id);
    }

    /**
     * Delete the dosyaTipi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DosyaTipi : {}", id);
        dosyaTipiRepository.delete(id);
        dosyaTipiSearchRepository.delete(id);
    }

    /**
     * Search for the dosyaTipi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosyaTipi> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DosyaTipis for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<DosyaTipi> result = dosyaTipiSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
