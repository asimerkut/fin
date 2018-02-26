package com.er.fin.service;

import com.er.fin.domain.BorcGrubu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BorcGrubu.
 */
public interface BorcGrubuService {

    /**
     * Save a borcGrubu.
     *
     * @param borcGrubu the entity to save
     * @return the persisted entity
     */
    BorcGrubu save(BorcGrubu borcGrubu);

    /**
     * Get all the borcGrubus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BorcGrubu> findAll(Pageable pageable);

    /**
     * Get the "id" borcGrubu.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BorcGrubu findOne(Long id);

    /**
     * Delete the "id" borcGrubu.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the borcGrubu corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BorcGrubu> search(String query, Pageable pageable);
}
