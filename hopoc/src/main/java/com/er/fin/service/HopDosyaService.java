package com.er.fin.service;

import com.er.fin.domain.HopDosya;
import java.util.List;

/**
 * Service Interface for managing HopDosya.
 */
public interface HopDosyaService {

    /**
     * Save a hopDosya.
     *
     * @param hopDosya the entity to save
     * @return the persisted entity
     */
    HopDosya save(HopDosya hopDosya);

    /**
     * Get all the hopDosyas.
     *
     * @return the list of entities
     */
    List<HopDosya> findAll();

    /**
     * Get the "id" hopDosya.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopDosya findOne(Long id);

    /**
     * Delete the "id" hopDosya.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hopDosya corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HopDosya> search(String query);
}
