package com.er.fin.service.impl;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.service.DosyaBorcKalemService;
import com.er.fin.domain.DosyaBorcKalem;
import com.er.fin.repository.DosyaBorcKalemRepository;
import com.er.fin.repository.search.DosyaBorcKalemSearchRepository;
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
 * Service Implementation for managing DosyaBorcKalem.
 */
@Service
@Transactional
public class DosyaBorcKalemServiceImpl implements DosyaBorcKalemService {

    private final Logger log = LoggerFactory.getLogger(DosyaBorcKalemServiceImpl.class);

    private final DosyaBorcKalemRepository dosyaBorcKalemRepository;

    private final DosyaBorcKalemRepository dosyaBorcKalemSearchRepository;

    public DosyaBorcKalemServiceImpl(DosyaBorcKalemRepository dosyaBorcKalemRepository, DosyaBorcKalemRepository dosyaBorcKalemSearchRepository) {
        this.dosyaBorcKalemRepository = dosyaBorcKalemRepository;
        this.dosyaBorcKalemSearchRepository = dosyaBorcKalemSearchRepository;
    }

    /**
     * Save a dosyaBorcKalem.
     *
     * @param dosyaBorcKalem the entity to save
     * @return the persisted entity
     */
    @Override
    public DosyaBorcKalem save(DosyaBorcKalem dosyaBorcKalem) {
        log.debug("Request to save DosyaBorcKalem : {}", dosyaBorcKalem);
        DosyaBorcKalem result = dosyaBorcKalemRepository.save(dosyaBorcKalem);
        dosyaBorcKalemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dosyaBorcKalems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosyaBorcKalem> findAll(Pageable pageable) {
        log.debug("Request to get all DosyaBorcKalems");
        pageable = FinUtil.getPageParam();
        return dosyaBorcKalemRepository.findAll(pageable);
    }

    /**
     * Get one dosyaBorcKalem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DosyaBorcKalem findOne(Long id) {
        log.debug("Request to get DosyaBorcKalem : {}", id);
        return dosyaBorcKalemRepository.findOne(id);
    }

    /**
     * Delete the dosyaBorcKalem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DosyaBorcKalem : {}", id);
        dosyaBorcKalemRepository.delete(id);
        dosyaBorcKalemSearchRepository.delete(id);
    }

    /**
     * Search for the dosyaBorcKalem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosyaBorcKalem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DosyaBorcKalems for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<DosyaBorcKalem> result = null;
        ComboSelDTO obj = FinUtil.getComboSelDTO(query);
        if (obj!=null){
            result = dosyaBorcKalemSearchRepository.findAllByDosyaBorcDosyaId(pageable, obj.getSelId());
        } else {
            result = dosyaBorcKalemSearchRepository.findAll(pageable);
        }
        return result;
    }
}
