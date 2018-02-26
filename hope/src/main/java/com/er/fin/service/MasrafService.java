package com.er.fin.service;

import com.er.fin.domain.Masraf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Masraf.
 */
public interface MasrafService {

    /**
     * Save a masraf.
     *
     * @param masraf the entity to save
     * @return the persisted entity
     */
    Masraf save(Masraf masraf);

    /**
     * Get all the masrafs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Masraf> findAll(Pageable pageable);

    /**
     * Get the "id" masraf.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Masraf findOne(Long id);

    /**
     * Delete the "id" masraf.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the masraf corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Masraf> search(String query, Pageable pageable);
}
