package com.er.fin.service;

import com.er.fin.domain.HopDosyaBorcKalem;
import java.util.List;

/**
 * Service Interface for managing HopDosyaBorcKalem.
 */
public interface HopDosyaBorcKalemService {

    /**
     * Save a hopDosyaBorcKalem.
     *
     * @param hopDosyaBorcKalem the entity to save
     * @return the persisted entity
     */
    HopDosyaBorcKalem save(HopDosyaBorcKalem hopDosyaBorcKalem);

    /**
     * Get all the hopDosyaBorcKalems.
     *
     * @return the list of entities
     */
    List<HopDosyaBorcKalem> findAll();

    /**
     * Get the "id" hopDosyaBorcKalem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopDosyaBorcKalem findOne(Long id);

    /**
     * Delete the "id" hopDosyaBorcKalem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hopDosyaBorcKalem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HopDosyaBorcKalem> search(String query);
}
