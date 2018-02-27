package com.er.fin.service.impl;

import com.er.fin.service.PerPersonService;
import com.er.fin.domain.PerPerson;
import com.er.fin.repository.PerPersonRepository;
import com.er.fin.repository.search.PerPersonSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PerPerson.
 */
@Service
@Transactional
public class PerPersonServiceImpl implements PerPersonService {

    private final Logger log = LoggerFactory.getLogger(PerPersonServiceImpl.class);

    private final PerPersonRepository perPersonRepository;

    private final PerPersonSearchRepository perPersonSearchRepository;

    public PerPersonServiceImpl(PerPersonRepository perPersonRepository, PerPersonSearchRepository perPersonSearchRepository) {
        this.perPersonRepository = perPersonRepository;
        this.perPersonSearchRepository = perPersonSearchRepository;
    }

    /**
     * Save a perPerson.
     *
     * @param perPerson the entity to save
     * @return the persisted entity
     */
    @Override
    public PerPerson save(PerPerson perPerson) {
        log.debug("Request to save PerPerson : {}", perPerson);
        PerPerson result = perPersonRepository.save(perPerson);
        perPersonSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the perPeople.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PerPerson> findAll() {
        log.debug("Request to get all PerPeople");
        return perPersonRepository.findAll();
    }

    /**
     * Get one perPerson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PerPerson findOne(Long id) {
        log.debug("Request to get PerPerson : {}", id);
        return perPersonRepository.findOne(id);
    }

    /**
     * Delete the perPerson by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PerPerson : {}", id);
        perPersonRepository.delete(id);
        perPersonSearchRepository.delete(id);
    }

    /**
     * Search for the perPerson corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PerPerson> search(String query) {
        log.debug("Request to search PerPeople for query {}", query);
        return StreamSupport
            .stream(perPersonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
