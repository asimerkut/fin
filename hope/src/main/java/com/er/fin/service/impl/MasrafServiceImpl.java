package com.er.fin.service.impl;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.service.MasrafService;
import com.er.fin.domain.Masraf;
import com.er.fin.repository.MasrafRepository;
import com.er.fin.repository.search.MasrafSearchRepository;
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
 * Service Implementation for managing Masraf.
 */
@Service
@Transactional
public class MasrafServiceImpl implements MasrafService {

    private final Logger log = LoggerFactory.getLogger(MasrafServiceImpl.class);

    private final MasrafRepository masrafRepository;

    private final MasrafRepository masrafSearchRepository;

    public MasrafServiceImpl(MasrafRepository masrafRepository, MasrafRepository masrafSearchRepository) {
        this.masrafRepository = masrafRepository;
        this.masrafSearchRepository = masrafSearchRepository;
    }

    /**
     * Save a masraf.
     *
     * @param masraf the entity to save
     * @return the persisted entity
     */
    @Override
    public Masraf save(Masraf masraf) {
        log.debug("Request to save Masraf : {}", masraf);
        Masraf result = masrafRepository.save(masraf);
        masrafSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the masrafs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Masraf> findAll(Pageable pageable) {
        log.debug("Request to get all Masrafs");
        pageable = FinUtil.getPageParam();
        return masrafRepository.findAll(pageable);
    }

    /**
     * Get one masraf by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Masraf findOne(Long id) {
        log.debug("Request to get Masraf : {}", id);
        return masrafRepository.findOne(id);
    }

    /**
     * Delete the masraf by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Masraf : {}", id);
        masrafRepository.delete(id);
        masrafSearchRepository.delete(id);
    }

    /**
     * Search for the masraf corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Masraf> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Masrafs for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<Masraf> result = null;
        ComboSelDTO obj = FinUtil.getComboSelDTO(query);
        if (obj!=null){
            result = masrafSearchRepository.findAllByDosyaId(pageable, obj.getSelId());
        } else {
            result = masrafSearchRepository.findAll(pageable);
        }
        return result;
    }
}
