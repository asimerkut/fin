package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.HopBorc;
import com.er.fin.service.HopBorcService;
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
 * REST controller for managing HopBorc.
 */
@RestController
@RequestMapping("/api")
public class HopBorcResource {

    private final Logger log = LoggerFactory.getLogger(HopBorcResource.class);

    private static final String ENTITY_NAME = "hopBorc";

    private final HopBorcService hopBorcService;

    public HopBorcResource(HopBorcService hopBorcService) {
        this.hopBorcService = hopBorcService;
    }

    /**
     * POST  /hop-borcs : Create a new hopBorc.
     *
     * @param hopBorc the hopBorc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hopBorc, or with status 400 (Bad Request) if the hopBorc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hop-borcs")
    @Timed
    public ResponseEntity<HopBorc> createHopBorc(@RequestBody HopBorc hopBorc) throws URISyntaxException {
        log.debug("REST request to save HopBorc : {}", hopBorc);
        if (hopBorc.getId() != null) {
            throw new BadRequestAlertException("A new hopBorc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HopBorc result = hopBorcService.save(hopBorc);
        return ResponseEntity.created(new URI("/api/hop-borcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hop-borcs : Updates an existing hopBorc.
     *
     * @param hopBorc the hopBorc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hopBorc,
     * or with status 400 (Bad Request) if the hopBorc is not valid,
     * or with status 500 (Internal Server Error) if the hopBorc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hop-borcs")
    @Timed
    public ResponseEntity<HopBorc> updateHopBorc(@RequestBody HopBorc hopBorc) throws URISyntaxException {
        log.debug("REST request to update HopBorc : {}", hopBorc);
        if (hopBorc.getId() == null) {
            return createHopBorc(hopBorc);
        }
        HopBorc result = hopBorcService.save(hopBorc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hopBorc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hop-borcs : get all the hopBorcs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hopBorcs in body
     */
    @GetMapping("/hop-borcs")
    @Timed
    public List<HopBorc> getAllHopBorcs() {
        log.debug("REST request to get all HopBorcs");
        return hopBorcService.findAll();
        }

    /**
     * GET  /hop-borcs/:id : get the "id" hopBorc.
     *
     * @param id the id of the hopBorc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hopBorc, or with status 404 (Not Found)
     */
    @GetMapping("/hop-borcs/{id}")
    @Timed
    public ResponseEntity<HopBorc> getHopBorc(@PathVariable Long id) {
        log.debug("REST request to get HopBorc : {}", id);
        HopBorc hopBorc = hopBorcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hopBorc));
    }

    /**
     * DELETE  /hop-borcs/:id : delete the "id" hopBorc.
     *
     * @param id the id of the hopBorc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hop-borcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteHopBorc(@PathVariable Long id) {
        log.debug("REST request to delete HopBorc : {}", id);
        hopBorcService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hop-borcs?query=:query : search for the hopBorc corresponding
     * to the query.
     *
     * @param query the query of the hopBorc search
     * @return the result of the search
     */
    @GetMapping("/_search/hop-borcs")
    @Timed
    public List<HopBorc> searchHopBorcs(@RequestParam String query) {
        log.debug("REST request to search HopBorcs for query {}", query);
        return hopBorcService.search(query);
    }

}
