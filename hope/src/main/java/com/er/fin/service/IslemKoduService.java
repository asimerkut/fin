package com.er.fin.service;

import com.er.fin.domain.IslemKodu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing IslemKodu.
 */
public interface IslemKoduService {

    /**
     * Save a islemKodu.
     *
     * @param islemKodu the entity to save
     * @return the persisted entity
     */
    IslemKodu save(IslemKodu islemKodu);

    /**
     * Get all the islemKodus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IslemKodu> findAll(Pageable pageable);

    /**
     * Get the "id" islemKodu.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IslemKodu findOne(Long id);

    /**
     * Delete the "id" islemKodu.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the islemKodu corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IslemKodu> search(String query, Pageable pageable);
}
