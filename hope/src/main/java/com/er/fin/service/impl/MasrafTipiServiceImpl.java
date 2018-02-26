package com.er.fin.service.impl;

import com.er.fin.service.MasrafTipiService;
import com.er.fin.domain.MasrafTipi;
import com.er.fin.repository.MasrafTipiRepository;
import com.er.fin.repository.search.MasrafTipiSearchRepository;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MasrafTipi.
 */
@Service
@Transactional
public class MasrafTipiServiceImpl implements MasrafTipiService {

    private final Logger log = LoggerFactory.getLogger(MasrafTipiServiceImpl.class);

    private final MasrafTipiRepository masrafTipiRepository;

    private final MasrafTipiSearchRepository masrafTipiSearchRepository;

    public MasrafTipiServiceImpl(MasrafTipiRepository masrafTipiRepository, MasrafTipiSearchRepository masrafTipiSearchRepository) {
        this.masrafTipiRepository = masrafTipiRepository;
        this.masrafTipiSearchRepository = masrafTipiSearchRepository;
    }

    /**
     * Save a masrafTipi.
     *
     * @param masrafTipi the entity to save
     * @return the persisted entity
     */
    @Override
    public MasrafTipi save(MasrafTipi masrafTipi) {
        log.debug("Request to save MasrafTipi : {}", masrafTipi);
        MasrafTipi result = masrafTipiRepository.save(masrafTipi);
        masrafTipiSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the masrafTipis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MasrafTipi> findAll(Pageable pageable) {
        log.debug("Request to get all MasrafTipis");
        pageable = FinUtil.getPageParam();
        return masrafTipiRepository.findAll(pageable);
    }

    /**
     * Get one masrafTipi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MasrafTipi findOne(Long id) {
        log.debug("Request to get MasrafTipi : {}", id);
        return masrafTipiRepository.findOne(id);
    }

    /**
     * Delete the masrafTipi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MasrafTipi : {}", id);
        masrafTipiRepository.delete(id);
        masrafTipiSearchRepository.delete(id);
    }

    /**
     * Search for the masrafTipi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MasrafTipi> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MasrafTipis for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<MasrafTipi> result = masrafTipiSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
