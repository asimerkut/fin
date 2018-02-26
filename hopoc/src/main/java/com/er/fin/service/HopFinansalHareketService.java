package com.er.fin.service;

import com.er.fin.domain.HopFinansalHareket;
import java.util.List;

/**
 * Service Interface for managing HopFinansalHareket.
 */
public interface HopFinansalHareketService {

    /**
     * Save a hopFinansalHareket.
     *
     * @param hopFinansalHareket the entity to save
     * @return the persisted entity
     */
    HopFinansalHareket save(HopFinansalHareket hopFinansalHareket);

    /**
     * Get all the hopFinansalHarekets.
     *
     * @return the list of entities
     */
    List<HopFinansalHareket> findAll();

    /**
     * Get the "id" hopFinansalHareket.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopFinansalHareket findOne(Long id);

    /**
     * Delete the "id" hopFinansalHareket.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hopFinansalHareket corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HopFinansalHareket> search(String query);
}
