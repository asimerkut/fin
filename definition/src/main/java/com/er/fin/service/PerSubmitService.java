package com.er.fin.service;

import com.er.fin.domain.PerSubmit;
import java.util.List;

/**
 * Service Interface for managing PerSubmit.
 */
public interface PerSubmitService {

    /**
     * Save a perSubmit.
     *
     * @param perSubmit the entity to save
     * @return the persisted entity
     */
    PerSubmit save(PerSubmit perSubmit);

    /**
     * Get all the perSubmits.
     *
     * @return the list of entities
     */
    List<PerSubmit> findAll();

    /**
     * Get the "id" perSubmit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PerSubmit findOne(Long id);

    /**
     * Delete the "id" perSubmit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the perSubmit corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PerSubmit> search(String query);
}
