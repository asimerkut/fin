package com.er.fin.service;

import com.er.fin.domain.HopFinansalHareketDetay;
import java.util.List;

/**
 * Service Interface for managing HopFinansalHareketDetay.
 */
public interface HopFinansalHareketDetayService {

    /**
     * Save a hopFinansalHareketDetay.
     *
     * @param hopFinansalHareketDetay the entity to save
     * @return the persisted entity
     */
    HopFinansalHareketDetay save(HopFinansalHareketDetay hopFinansalHareketDetay);

    /**
     * Get all the hopFinansalHareketDetays.
     *
     * @return the list of entities
     */
    List<HopFinansalHareketDetay> findAll();

    /**
     * Get the "id" hopFinansalHareketDetay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopFinansalHareketDetay findOne(Long id);

    /**
     * Delete the "id" hopFinansalHareketDetay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hopFinansalHareketDetay corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HopFinansalHareketDetay> search(String query);
}
