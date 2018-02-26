package com.er.fin.service;

import com.er.fin.domain.Dosya;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Dosya.
 */
public interface DosyaService {

    /**
     * Save a dosya.
     *
     * @param dosya the entity to save
     * @return the persisted entity
     */
    Dosya save(Dosya dosya);

    /**
     * Get all the dosyas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Dosya> findAll(Pageable pageable);

    /**
     * Get the "id" dosya.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Dosya findOne(Long id);

    /**
     * Delete the "id" dosya.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dosya corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Dosya> search(String query, Pageable pageable);
}
