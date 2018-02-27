package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DefRelation;
import com.er.fin.service.DefRelationService;
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
 * REST controller for managing DefRelation.
 */
@RestController
@RequestMapping("/api")
public class DefRelationResource {

    private final Logger log = LoggerFactory.getLogger(DefRelationResource.class);

    private static final String ENTITY_NAME = "defRelation";

    private final DefRelationService defRelationService;

    public DefRelationResource(DefRelationService defRelationService) {
        this.defRelationService = defRelationService;
    }

    /**
     * POST  /def-relations : Create a new defRelation.
     *
     * @param defRelation the defRelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new defRelation, or with status 400 (Bad Request) if the defRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/def-relations")
    @Timed
    public ResponseEntity<DefRelation> createDefRelation(@Valid @RequestBody DefRelation defRelation) throws URISyntaxException {
        log.debug("REST request to save DefRelation : {}", defRelation);
        if (defRelation.getId() != null) {
            throw new BadRequestAlertException("A new defRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefRelation result = defRelationService.save(defRelation);
        return ResponseEntity.created(new URI("/api/def-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /def-relations : Updates an existing defRelation.
     *
     * @param defRelation the defRelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated defRelation,
     * or with status 400 (Bad Request) if the defRelation is not valid,
     * or with status 500 (Internal Server Error) if the defRelation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/def-relations")
    @Timed
    public ResponseEntity<DefRelation> updateDefRelation(@Valid @RequestBody DefRelation defRelation) throws URISyntaxException {
        log.debug("REST request to update DefRelation : {}", defRelation);
        if (defRelation.getId() == null) {
            return createDefRelation(defRelation);
        }
        DefRelation result = defRelationService.save(defRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, defRelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /def-relations : get all the defRelations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of defRelations in body
     */
    @GetMapping("/def-relations")
    @Timed
    public List<DefRelation> getAllDefRelations() {
        log.debug("REST request to get all DefRelations");
        return defRelationService.findAll();
        }

    /**
     * GET  /def-relations/:id : get the "id" defRelation.
     *
     * @param id the id of the defRelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the defRelation, or with status 404 (Not Found)
     */
    @GetMapping("/def-relations/{id}")
    @Timed
    public ResponseEntity<DefRelation> getDefRelation(@PathVariable Long id) {
        log.debug("REST request to get DefRelation : {}", id);
        DefRelation defRelation = defRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(defRelation));
    }

    /**
     * DELETE  /def-relations/:id : delete the "id" defRelation.
     *
     * @param id the id of the defRelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/def-relations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDefRelation(@PathVariable Long id) {
        log.debug("REST request to delete DefRelation : {}", id);
        defRelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/def-relations?query=:query : search for the defRelation corresponding
     * to the query.
     *
     * @param query the query of the defRelation search
     * @return the result of the search
     */
    @GetMapping("/_search/def-relations")
    @Timed
    public List<DefRelation> searchDefRelations(@RequestParam String query) {
        log.debug("REST request to search DefRelations for query {}", query);
        return defRelationService.search(query);
    }

}
