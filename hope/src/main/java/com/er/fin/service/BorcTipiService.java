package com.er.fin.service;

import com.er.fin.domain.BorcTipi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BorcTipi.
 */
public interface BorcTipiService {

    /**
     * Save a borcTipi.
     *
     * @param borcTipi the entity to save
     * @return the persisted entity
     */
    BorcTipi save(BorcTipi borcTipi);

    /**
     * Get all the borcTipis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BorcTipi> findAll(Pageable pageable);

    /**
     * Get the "id" borcTipi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BorcTipi findOne(Long id);

    /**
     * Delete the "id" borcTipi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the borcTipi corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BorcTipi> search(String query, Pageable pageable);
}
