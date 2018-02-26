package com.er.fin.service;

import com.er.fin.domain.FinansalHareket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FinansalHareket.
 */
public interface FinansalHareketService {

    /**
     * Save a finansalHareket.
     *
     * @param finansalHareket the entity to save
     * @return the persisted entity
     */
    FinansalHareket save(FinansalHareket finansalHareket);

    /**
     * Get all the finansalHarekets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FinansalHareket> findAll(Pageable pageable);

    /**
     * Get the "id" finansalHareket.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FinansalHareket findOne(Long id);

    /**
     * Delete the "id" finansalHareket.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the finansalHareket corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FinansalHareket> search(String query, Pageable pageable);
}
