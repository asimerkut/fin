package com.er.fin.service.impl;

import com.er.fin.service.BorcKalemService;
import com.er.fin.domain.BorcKalem;
import com.er.fin.repository.BorcKalemRepository;
import com.er.fin.repository.search.BorcKalemSearchRepository;
import com.er.fin.service.dto.FinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BorcKalem.
 */
@Service
@Transactional
public class BorcKalemServiceImpl implements BorcKalemService {

    private final Logger log = LoggerFactory.getLogger(BorcKalemServiceImpl.class);

    private final BorcKalemRepository borcKalemRepository;

    private final BorcKalemSearchRepository borcKalemSearchRepository;

    public BorcKalemServiceImpl(BorcKalemRepository borcKalemRepository, BorcKalemSearchRepository borcKalemSearchRepository) {
        this.borcKalemRepository = borcKalemRepository;
        this.borcKalemSearchRepository = borcKalemSearchRepository;
    }

    /**
     * Save a borcKalem.
     *
     * @param borcKalem the entity to save
     * @return the persisted entity
     */
    @Override
    public BorcKalem save(BorcKalem borcKalem) {
        log.debug("Request to save BorcKalem : {}", borcKalem);
        BorcKalem result = borcKalemRepository.save(borcKalem);
        borcKalemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the borcKalems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BorcKalem> findAll(Pageable pageable) {
        log.debug("Request to get all BorcKalems");
        pageable = FinUtil.getPageParam();
        return borcKalemRepository.findAll(pageable);
    }

    /**
     * Get one borcKalem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BorcKalem findOne(Long id) {
        log.debug("Request to get BorcKalem : {}", id);
        return borcKalemRepository.findOne(id);
    }

    /**
     * Delete the borcKalem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BorcKalem : {}", id);
        borcKalemRepository.delete(id);
        borcKalemSearchRepository.delete(id);
    }

    /**
     * Search for the borcKalem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BorcKalem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BorcKalems for query {}", query);
        pageable = FinUtil.getPageParam();
        Page<BorcKalem> result = borcKalemSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
