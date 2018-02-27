package com.er.fin.service;

import com.er.fin.domain.PerPlan;
import java.util.List;

/**
 * Service Interface for managing PerPlan.
 */
public interface PerPlanService {

    /**
     * Save a perPlan.
     *
     * @param perPlan the entity to save
     * @return the persisted entity
     */
    PerPlan save(PerPlan perPlan);

    /**
     * Get all the perPlans.
     *
     * @return the list of entities
     */
    List<PerPlan> findAll();

    /**
     * Get the "id" perPlan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PerPlan findOne(Long id);

    /**
     * Delete the "id" perPlan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the perPlan corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PerPlan> search(String query);
}
