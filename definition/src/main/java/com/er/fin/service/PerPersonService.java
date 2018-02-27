package com.er.fin.service;

import com.er.fin.domain.PerPerson;
import java.util.List;

/**
 * Service Interface for managing PerPerson.
 */
public interface PerPersonService {

    /**
     * Save a perPerson.
     *
     * @param perPerson the entity to save
     * @return the persisted entity
     */
    PerPerson save(PerPerson perPerson);

    /**
     * Get all the perPeople.
     *
     * @return the list of entities
     */
    List<PerPerson> findAll();

    /**
     * Get the "id" perPerson.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PerPerson findOne(Long id);

    /**
     * Delete the "id" perPerson.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the perPerson corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PerPerson> search(String query);
}
