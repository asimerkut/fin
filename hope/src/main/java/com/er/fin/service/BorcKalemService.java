package com.er.fin.service;

import com.er.fin.domain.BorcKalem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BorcKalem.
 */
public interface BorcKalemService {

    /**
     * Save a borcKalem.
     *
     * @param borcKalem the entity to save
     * @return the persisted entity
     */
    BorcKalem save(BorcKalem borcKalem);

    /**
     * Get all the borcKalems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BorcKalem> findAll(Pageable pageable);

    /**
     * Get the "id" borcKalem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BorcKalem findOne(Long id);

    /**
     * Delete the "id" borcKalem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the borcKalem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BorcKalem> search(String query, Pageable pageable);
}
