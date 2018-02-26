package com.er.fin.service.impl;

import com.er.fin.service.BorcTipiService;
import com.er.fin.domain.BorcTipi;
import com.er.fin.repository.BorcTipiRepository;
import com.er.fin.repository.search.BorcTipiSearchRepository;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BorcTipi.
 */
@Service
@Transactional
public class BorcTipiServiceImpl implements BorcTipiService {

    private final Logger log = LoggerFactory.getLogger(BorcTipiServiceImpl.class);

    private final BorcTipiRepository borcTipiRepository;

    private final BorcTipiSearchRepository borcTipiSearchRepository;

    public BorcTipiServiceImpl(BorcTipiRepository borcTipiRepository, BorcTipiSearchRepository borcTipiSearchRepository) {
        this.borcTipiRepository = borcTipiRepository;
        this.borcTipiSearchRepository = borcTipiSearchRepository;
    }

    /**
     * Save a borcTipi.
     *
     * @param borcTipi the entity to save
     * @return the persisted entity
     */
    @Override
    public BorcTipi save(BorcTipi borcTipi) {
        log.debug("Request to save BorcTipi : {}", borcTipi);
        BorcTipi result = borcTipiRepository.save(borcTipi);
        borcTipiSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the borcTipis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BorcTipi> findAll(Pageable pageable) {
        log.debug("Request to get all BorcTipis");
        pageable = FinUtil.getPageParam();
        return borcTipiRepository.findAll(pageable);
    }

    /**
     * Get one borcTipi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BorcTipi findOne(Long id) {
        log.debug("Request to get BorcTipi : {}", id);
        return borcTipiRepository.findOne(id);
    }

    /**
     * Delete the borcTipi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BorcTipi : {}", id);
        borcTipiRepository.delete(id);
        borcTipiSearchRepository.delete(id);
    }

    /**
     * Search for the borcTipi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BorcTipi> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BorcTipis for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<BorcTipi> result = borcTipiSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
