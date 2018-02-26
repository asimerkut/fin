package com.er.fin.service.impl;

import com.er.fin.service.DefItemService;
import com.er.fin.domain.DefItem;
import com.er.fin.repository.DefItemRepository;
import com.er.fin.repository.search.DefItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DefItem.
 */
@Service
@Transactional
public class DefItemServiceImpl implements DefItemService {

    private final Logger log = LoggerFactory.getLogger(DefItemServiceImpl.class);

    private final DefItemRepository defItemRepository;

    private final DefItemSearchRepository defItemSearchRepository;

    public DefItemServiceImpl(DefItemRepository defItemRepository, DefItemSearchRepository defItemSearchRepository) {
        this.defItemRepository = defItemRepository;
        this.defItemSearchRepository = defItemSearchRepository;
    }

    /**
     * Save a defItem.
     *
     * @param defItem the entity to save
     * @return the persisted entity
     */
    @Override
    public DefItem save(DefItem defItem) {
        log.debug("Request to save DefItem : {}", defItem);
        DefItem result = defItemRepository.save(defItem);
        defItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the defItems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DefItem> findAll() {
        log.debug("Request to get all DefItems");
        return defItemRepository.findAll();
    }

    /**
     * Get one defItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DefItem findOne(Long id) {
        log.debug("Request to get DefItem : {}", id);
        return defItemRepository.findOne(id);
    }

    /**
     * Delete the defItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DefItem : {}", id);
        defItemRepository.delete(id);
        defItemSearchRepository.delete(id);
    }

    /**
     * Search for the defItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DefItem> search(String query) {
        log.debug("Request to search DefItems for query {}", query);
        return StreamSupport
            .stream(defItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
