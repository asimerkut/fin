package com.er.fin.service;

import com.er.fin.domain.HopBorc;
import java.util.List;

/**
 * Service Interface for managing HopBorc.
 */
public interface HopBorcService {

    /**
     * Save a hopBorc.
     *
     * @param hopBorc the entity to save
     * @return the persisted entity
     */
    HopBorc save(HopBorc hopBorc);

    /**
     * Get all the hopBorcs.
     *
     * @return the list of entities
     */
    List<HopBorc> findAll();

    /**
     * Get the "id" hopBorc.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopBorc findOne(Long id);

    /**
     * Delete the "id" hopBorc.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hopBorc corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HopBorc> search(String query);
}
