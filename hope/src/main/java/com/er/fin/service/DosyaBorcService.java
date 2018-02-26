package com.er.fin.service;

import com.er.fin.domain.DosyaBorc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Service Interface for managing DosyaBorc.
 */
public interface DosyaBorcService {

    /**
     * Save a dosyaBorc.
     *
     * @param dosyaBorc the entity to save
     * @return the persisted entity
     */
    DosyaBorc save(DosyaBorc dosyaBorc);

    /**
     * Get all the dosyaBorcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DosyaBorc> findAll(Pageable pageable);

    /**
     * Get the "id" dosyaBorc.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DosyaBorc findOne(Long id);

    /**
     * Delete the "id" dosyaBorc.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dosyaBorc corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DosyaBorc> search(String query, Pageable pageable);
}
