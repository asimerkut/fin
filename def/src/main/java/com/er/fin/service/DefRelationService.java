package com.er.fin.service;

import com.er.fin.domain.DefRelation;
import java.util.List;

/**
 * Service Interface for managing DefRelation.
 */
public interface DefRelationService {

    /**
     * Save a defRelation.
     *
     * @param defRelation the entity to save
     * @return the persisted entity
     */
    DefRelation save(DefRelation defRelation);

    /**
     * Get all the defRelations.
     *
     * @return the list of entities
     */
    List<DefRelation> findAll();

    /**
     * Get the "id" defRelation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DefRelation findOne(Long id);

    /**
     * Delete the "id" defRelation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the defRelation corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<DefRelation> search(String query);
}
