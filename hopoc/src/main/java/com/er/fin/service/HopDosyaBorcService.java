package com.er.fin.service;

import com.er.fin.domain.HopDosyaBorc;
import java.util.List;

/**
 * Service Interface for managing HopDosyaBorc.
 */
public interface HopDosyaBorcService {

    /**
     * Save a hopDosyaBorc.
     *
     * @param hopDosyaBorc the entity to save
     * @return the persisted entity
     */
    HopDosyaBorc save(HopDosyaBorc hopDosyaBorc);

    /**
     * Get all the hopDosyaBorcs.
     *
     * @return the list of entities
     */
    List<HopDosyaBorc> findAll();

    /**
     * Get the "id" hopDosyaBorc.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopDosyaBorc findOne(Long id);

    /**
     * Delete the "id" hopDosyaBorc.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hopDosyaBorc corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HopDosyaBorc> search(String query);
}
