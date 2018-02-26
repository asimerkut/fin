package com.er.fin.service;

import com.er.fin.domain.DosyaBorcKalem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DosyaBorcKalem.
 */
public interface DosyaBorcKalemService {

    /**
     * Save a dosyaBorcKalem.
     *
     * @param dosyaBorcKalem the entity to save
     * @return the persisted entity
     */
    DosyaBorcKalem save(DosyaBorcKalem dosyaBorcKalem);

    /**
     * Get all the dosyaBorcKalems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DosyaBorcKalem> findAll(Pageable pageable);

    /**
     * Get the "id" dosyaBorcKalem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DosyaBorcKalem findOne(Long id);

    /**
     * Delete the "id" dosyaBorcKalem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dosyaBorcKalem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DosyaBorcKalem> search(String query, Pageable pageable);
}
