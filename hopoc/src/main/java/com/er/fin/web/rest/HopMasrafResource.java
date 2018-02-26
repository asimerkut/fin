package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.HopMasraf;
import com.er.fin.service.HopMasrafService;
import com.er.fin.web.rest.errors.BadRequestAlertException;
import com.er.fin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HopMasraf.
 */
@RestController
@RequestMapping("/api")
public class HopMasrafResource {

    private final Logger log = LoggerFactory.getLogger(HopMasrafResource.class);

    private static final String ENTITY_NAME = "hopMasraf";

    private final HopMasrafService hopMasrafService;

    public HopMasrafResource(HopMasrafService hopMasrafService) {
        this.hopMasrafService = hopMasrafService;
    }

    /**
     * POST  /hop-masrafs : Create a new hopMasraf.
     *
     * @param hopMasraf the hopMasraf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hopMasraf, or with status 400 (Bad Request) if the hopMasraf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hop-masrafs")
    @Timed
    public ResponseEntity<HopMasraf> createHopMasraf(@RequestBody HopMasraf hopMasraf) throws URISyntaxException {
        log.debug("REST request to save HopMasraf : {}", hopMasraf);
        if (hopMasraf.getId() != null) {
            throw new BadRequestAlertException("A new hopMasraf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HopMasraf result = hopMasrafService.save(hopMasraf);
        return ResponseEntity.created(new URI("/api/hop-masrafs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hop-masrafs : Updates an existing hopMasraf.
     *
     * @param hopMasraf the hopMasraf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hopMasraf,
     * or with status 400 (Bad Request) if the hopMasraf is not valid,
     * or with status 500 (Internal Server Error) if the hopMasraf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hop-masrafs")
    @Timed
    public ResponseEntity<HopMasraf> updateHopMasraf(@RequestBody HopMasraf hopMasraf) throws URISyntaxException {
        log.debug("REST request to update HopMasraf : {}", hopMasraf);
        if (hopMasraf.getId() == null) {
            return createHopMasraf(hopMasraf);
        }
        HopMasraf result = hopMasrafService.save(hopMasraf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hopMasraf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hop-masrafs : get all the hopMasrafs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hopMasrafs in body
     */
    @GetMapping("/hop-masrafs")
    @Timed
    public List<HopMasraf> getAllHopMasrafs() {
        log.debug("REST request to get all HopMasrafs");
        return hopMasrafService.findAll();
        }

    /**
     * GET  /hop-masrafs/:id : get the "id" hopMasraf.
     *
     * @param id the id of the hopMasraf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hopMasraf, or with status 404 (Not Found)
     */
    @GetMapping("/hop-masrafs/{id}")
    @Timed
    public ResponseEntity<HopMasraf> getHopMasraf(@PathVariable Long id) {
        log.debug("REST request to get HopMasraf : {}", id);
        HopMasraf hopMasraf = hopMasrafService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hopMasraf));
    }

    /**
     * DELETE  /hop-masrafs/:id : delete the "id" hopMasraf.
     *
     * @param id the id of the hopMasraf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hop-masrafs/{id}")
    @Timed
    public ResponseEntity<Void> deleteHopMasraf(@PathVariable Long id) {
        log.debug("REST request to delete HopMasraf : {}", id);
        hopMasrafService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hop-masrafs?query=:query : search for the hopMasraf corresponding
     * to the query.
     *
     * @param query the query of the hopMasraf search
     * @return the result of the search
     */
    @GetMapping("/_search/hop-masrafs")
    @Timed
    public List<HopMasraf> searchHopMasrafs(@RequestParam String query) {
        log.debug("REST request to search HopMasrafs for query {}", query);
        return hopMasrafService.search(query);
    }

}
