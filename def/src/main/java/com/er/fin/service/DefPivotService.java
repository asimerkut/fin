package com.er.fin.service;

import com.er.fin.domain.DefPivot;
import com.er.fin.service.dto.PivotDataDTO;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing DefPivot.
 */
public interface DefPivotService {

    List<String> getFieldSet(String sql);
    PivotDataDTO getSqlData(String sql);

    /**
     * Save a defPivot.
     *
     * @param defPivot the entity to save
     * @return the persisted entity
     */
    DefPivot save(DefPivot defPivot);

    /**
     * Get all the defPivots.
     *
     * @return the list of entities
     */
    List<DefPivot> findAll();

    /**
     * Get the "id" defPivot.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DefPivot findOne(Long id);

    /**
     * Delete the "id" defPivot.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the defPivot corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<DefPivot> search(String query);
}
