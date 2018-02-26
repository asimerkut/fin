package com.er.fin.service;

import com.er.fin.domain.HopMasraf;
import java.util.List;

/**
 * Service Interface for managing HopMasraf.
 */
public interface HopMasrafService {

    /**
     * Save a hopMasraf.
     *
     * @param hopMasraf the entity to save
     * @return the persisted entity
     */
    HopMasraf save(HopMasraf hopMasraf);

    /**
     * Get all the hopMasrafs.
     *
     * @return the list of entities
     */
    List<HopMasraf> findAll();

    /**
     * Get the "id" hopMasraf.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopMasraf findOne(Long id);

    /**
     * Delete the "id" hopMasraf.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hopMasraf corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HopMasraf> search(String query);
}
