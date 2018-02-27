package com.er.fin.service;

import com.er.fin.domain.PerExcuse;
import java.util.List;

/**
 * Service Interface for managing PerExcuse.
 */
public interface PerExcuseService {

    /**
     * Save a perExcuse.
     *
     * @param perExcuse the entity to save
     * @return the persisted entity
     */
    PerExcuse save(PerExcuse perExcuse);

    /**
     * Get all the perExcuses.
     *
     * @return the list of entities
     */
    List<PerExcuse> findAll();

    /**
     * Get the "id" perExcuse.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PerExcuse findOne(Long id);

    /**
     * Delete the "id" perExcuse.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the perExcuse corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PerExcuse> search(String query);
}
