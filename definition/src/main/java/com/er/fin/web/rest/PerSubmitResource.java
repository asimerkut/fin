package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.PerSubmit;
import com.er.fin.service.PerSubmitService;
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
 * REST controller for managing PerSubmit.
 */
@RestController
@RequestMapping("/api")
public class PerSubmitResource {

    private final Logger log = LoggerFactory.getLogger(PerSubmitResource.class);

    private static final String ENTITY_NAME = "perSubmit";

    private final PerSubmitService perSubmitService;

    public PerSubmitResource(PerSubmitService perSubmitService) {
        this.perSubmitService = perSubmitService;
    }

    /**
     * POST  /per-submits : Create a new perSubmit.
     *
     * @param perSubmit the perSubmit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perSubmit, or with status 400 (Bad Request) if the perSubmit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/per-submits")
    @Timed
    public ResponseEntity<PerSubmit> createPerSubmit(@Valid @RequestBody PerSubmit perSubmit) throws URISyntaxException {
        log.debug("REST request to save PerSubmit : {}", perSubmit);
        if (perSubmit.getId() != null) {
            throw new BadRequestAlertException("A new perSubmit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerSubmit result = perSubmitService.save(perSubmit);
        return ResponseEntity.created(new URI("/api/per-submits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /per-submits : Updates an existing perSubmit.
     *
     * @param perSubmit the perSubmit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perSubmit,
     * or with status 400 (Bad Request) if the perSubmit is not valid,
     * or with status 500 (Internal Server Error) if the perSubmit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/per-submits")
    @Timed
    public ResponseEntity<PerSubmit> updatePerSubmit(@Valid @RequestBody PerSubmit perSubmit) throws URISyntaxException {
        log.debug("REST request to update PerSubmit : {}", perSubmit);
        if (perSubmit.getId() == null) {
            return createPerSubmit(perSubmit);
        }
        PerSubmit result = perSubmitService.save(perSubmit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perSubmit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /per-submits : get all the perSubmits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of perSubmits in body
     */
    @GetMapping("/per-submits")
    @Timed
    public List<PerSubmit> getAllPerSubmits() {
        log.debug("REST request to get all PerSubmits");
        return perSubmitService.findAll();
        }

    /**
     * GET  /per-submits/:id : get the "id" perSubmit.
     *
     * @param id the id of the perSubmit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perSubmit, or with status 404 (Not Found)
     */
    @GetMapping("/per-submits/{id}")
    @Timed
    public ResponseEntity<PerSubmit> getPerSubmit(@PathVariable Long id) {
        log.debug("REST request to get PerSubmit : {}", id);
        PerSubmit perSubmit = perSubmitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perSubmit));
    }

    /**
     * DELETE  /per-submits/:id : delete the "id" perSubmit.
     *
     * @param id the id of the perSubmit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/per-submits/{id}")
    @Timed
    public ResponseEntity<Void> deletePerSubmit(@PathVariable Long id) {
        log.debug("REST request to delete PerSubmit : {}", id);
        perSubmitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/per-submits?query=:query : search for the perSubmit corresponding
     * to the query.
     *
     * @param query the query of the perSubmit search
     * @return the result of the search
     */
    @GetMapping("/_search/per-submits")
    @Timed
    public List<PerSubmit> searchPerSubmits(@RequestParam String query) {
        log.debug("REST request to search PerSubmits for query {}", query);
        return perSubmitService.search(query);
    }

}
