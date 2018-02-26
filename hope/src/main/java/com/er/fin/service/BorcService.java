package com.er.fin.service;

import com.er.fin.domain.Borc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Borc.
 */
public interface BorcService {

    /**
     * Save a borc.
     *
     * @param borc the entity to save
     * @return the persisted entity
     */
    Borc save(Borc borc);

    /**
     * Get all the borcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Borc> findAll(Pageable pageable);

    /**
     * Get the "id" borc.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Borc findOne(Long id);

    /**
     * Delete the "id" borc.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the borc corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Borc> search(String query, Pageable pageable);
}
