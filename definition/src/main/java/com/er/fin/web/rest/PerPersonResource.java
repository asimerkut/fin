package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.PerPerson;
import com.er.fin.service.PerPersonService;
import com.er.fin.web.rest.errors.BadRequestAlertException;
import com.er.fin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PerPerson.
 */
@RestController
@RequestMapping("/api")
public class PerPersonResource {

    private final Logger log = LoggerFactory.getLogger(PerPersonResource.class);

    private static final String ENTITY_NAME = "perPerson";

    private final PerPersonService perPersonService;

    public PerPersonResource(PerPersonService perPersonService) {
        this.perPersonService = perPersonService;
    }

    /**
     * POST  /per-people : Create a new perPerson.
     *
     * @param perPerson the perPerson to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perPerson, or with status 400 (Bad Request) if the perPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/per-people")
    @Timed
    public ResponseEntity<PerPerson> createPerPerson(@Valid @RequestBody PerPerson perPerson) throws URISyntaxException {
        log.debug("REST request to save PerPerson : {}", perPerson);
        if (perPerson.getId() != null) {
            throw new BadRequestAlertException("A new perPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerPerson result = perPersonService.save(perPerson);
        return ResponseEntity.created(new URI("/api/per-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /per-people : Updates an existing perPerson.
     *
     * @param perPerson the perPerson to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perPerson,
     * or with status 400 (Bad Request) if the perPerson is not valid,
     * or with status 500 (Internal Server Error) if the perPerson couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/per-people")
    @Timed
    public ResponseEntity<PerPerson> updatePerPerson(@Valid @RequestBody PerPerson perPerson) throws URISyntaxException {
        log.debug("REST request to update PerPerson : {}", perPerson);
        if (perPerson.getId() == null) {
            return createPerPerson(perPerson);
        }
        PerPerson result = perPersonService.save(perPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perPerson.getId().toString()))
            .body(result);
    }

    /**
     * GET  /per-people : get all the perPeople.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of perPeople in body
     */
    @GetMapping("/per-people")
    @Timed
    public List<PerPerson> getAllPerPeople() {
        log.debug("REST request to get all PerPeople");
        return perPersonService.findAll();
        }

    /**
     * GET  /per-people/:id : get the "id" perPerson.
     *
     * @param id the id of the perPerson to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perPerson, or with status 404 (Not Found)
     */
    @GetMapping("/per-people/{id}")
    @Timed
    public ResponseEntity<PerPerson> getPerPerson(@PathVariable Long id) {
        log.debug("REST request to get PerPerson : {}", id);
        PerPerson perPerson = perPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perPerson));
    }

    /**
     * DELETE  /per-people/:id : delete the "id" perPerson.
     *
     * @param id the id of the perPerson to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/per-people/{id}")
    @Timed
    public ResponseEntity<Void> deletePerPerson(@PathVariable Long id) {
        log.debug("REST request to delete PerPerson : {}", id);
        perPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/per-people?query=:query : search for the perPerson corresponding
     * to the query.
     *
     * @param query the query of the perPerson search
     * @return the result of the search
     */
    @GetMapping("/_search/per-people")
    @Timed
    public List<PerPerson> searchPerPeople(@RequestParam String query) {
        log.debug("REST request to search PerPeople for query {}", query);
        return perPersonService.search(query);
    }

}
