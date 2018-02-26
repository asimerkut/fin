package com.er.fin.service;

import com.er.fin.domain.DosyaTipi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DosyaTipi.
 */
public interface DosyaTipiService {

    /**
     * Save a dosyaTipi.
     *
     * @param dosyaTipi the entity to save
     * @return the persisted entity
     */
    DosyaTipi save(DosyaTipi dosyaTipi);

    /**
     * Get all the dosyaTipis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DosyaTipi> findAll(Pageable pageable);

    /**
     * Get the "id" dosyaTipi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DosyaTipi findOne(Long id);

    /**
     * Delete the "id" dosyaTipi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dosyaTipi corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DosyaTipi> search(String query, Pageable pageable);
}
