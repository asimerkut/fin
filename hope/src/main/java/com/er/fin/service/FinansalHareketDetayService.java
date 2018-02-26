package com.er.fin.service;

import com.er.fin.domain.FinansalHareketDetay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FinansalHareketDetay.
 */
public interface FinansalHareketDetayService {

    /**
     * Save a finansalHareketDetay.
     *
     * @param finansalHareketDetay the entity to save
     * @return the persisted entity
     */
    FinansalHareketDetay save(FinansalHareketDetay finansalHareketDetay);

    /**
     * Get all the finansalHareketDetays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FinansalHareketDetay> findAll(Pageable pageable);

    /**
     * Get the "id" finansalHareketDetay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FinansalHareketDetay findOne(Long id);

    /**
     * Delete the "id" finansalHareketDetay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the finansalHareketDetay corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FinansalHareketDetay> search(String query, Pageable pageable);
}
