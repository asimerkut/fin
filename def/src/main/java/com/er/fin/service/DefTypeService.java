package com.er.fin.service;

import com.er.fin.domain.DefType;
import java.util.List;

/**
 * Service Interface for managing DefType.
 */
public interface DefTypeService {

    /**
     * Save a defType.
     *
     * @param defType the entity to save
     * @return the persisted entity
     */
    DefType save(DefType defType);

    /**
     * Get all the defTypes.
     *
     * @return the list of entities
     */
    List<DefType> findAll();

    /**
     * Get the "id" defType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DefType findOne(Long id);

    /**
     * Delete the "id" defType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the defType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<DefType> search(String query);
}
