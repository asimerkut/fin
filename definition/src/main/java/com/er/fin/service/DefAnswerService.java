package com.er.fin.service;

import com.er.fin.domain.DefAnswer;
import java.util.List;

/**
 * Service Interface for managing DefAnswer.
 */
public interface DefAnswerService {

    /**
     * Save a defAnswer.
     *
     * @param defAnswer the entity to save
     * @return the persisted entity
     */
    DefAnswer save(DefAnswer defAnswer);

    /**
     * Get all the defAnswers.
     *
     * @return the list of entities
     */
    List<DefAnswer> findAll();

    /**
     * Get the "id" defAnswer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DefAnswer findOne(Long id);

    /**
     * Delete the "id" defAnswer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the defAnswer corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<DefAnswer> search(String query);
}
