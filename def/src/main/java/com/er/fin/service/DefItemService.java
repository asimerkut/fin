package com.er.fin.service;

import com.er.fin.domain.DefItem;
import java.util.List;

/**
 * Service Interface for managing DefItem.
 */
public interface DefItemService {

    /**
     * Save a defItem.
     *
     * @param defItem the entity to save
     * @return the persisted entity
     */
    DefItem save(DefItem defItem);

    /**
     * Get all the defItems.
     *
     * @return the list of entities
     */
    List<DefItem> findAll();

    /**
     * Get the "id" defItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DefItem findOne(Long id);

    /**
     * Delete the "id" defItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the defItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<DefItem> search(String query);
}
