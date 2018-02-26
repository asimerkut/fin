package com.er.fin.service;

import com.er.fin.domain.MasrafTipi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MasrafTipi.
 */
public interface MasrafTipiService {

    /**
     * Save a masrafTipi.
     *
     * @param masrafTipi the entity to save
     * @return the persisted entity
     */
    MasrafTipi save(MasrafTipi masrafTipi);

    /**
     * Get all the masrafTipis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MasrafTipi> findAll(Pageable pageable);

    /**
     * Get the "id" masrafTipi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MasrafTipi findOne(Long id);

    /**
     * Delete the "id" masrafTipi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the masrafTipi corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MasrafTipi> search(String query, Pageable pageable);
}
