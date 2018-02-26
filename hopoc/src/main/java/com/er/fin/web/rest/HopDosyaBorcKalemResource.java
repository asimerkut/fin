package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.HopDosyaBorcKalem;
import com.er.fin.service.HopDosyaBorcKalemService;
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
 * REST controller for managing HopDosyaBorcKalem.
 */
@RestController
@RequestMapping("/api")
public class HopDosyaBorcKalemResource {

    private final Logger log = LoggerFactory.getLogger(HopDosyaBorcKalemResource.class);

    private static final String ENTITY_NAME = "hopDosyaBorcKalem";

    private final HopDosyaBorcKalemService hopDosyaBorcKalemService;

    public HopDosyaBorcKalemResource(HopDosyaBorcKalemService hopDosyaBorcKalemService) {
        this.hopDosyaBorcKalemService = hopDosyaBorcKalemService;
    }

    /**
     * POST  /hop-dosya-borc-kalems : Create a new hopDosyaBorcKalem.
     *
     * @param hopDosyaBorcKalem the hopDosyaBorcKalem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hopDosyaBorcKalem, or with status 400 (Bad Request) if the hopDosyaBorcKalem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hop-dosya-borc-kalems")
    @Timed
    public ResponseEntity<HopDosyaBorcKalem> createHopDosyaBorcKalem(@RequestBody HopDosyaBorcKalem hopDosyaBorcKalem) throws URISyntaxException {
        log.debug("REST request to save HopDosyaBorcKalem : {}", hopDosyaBorcKalem);
        if (hopDosyaBorcKalem.getId() != null) {
            throw new BadRequestAlertException("A new hopDosyaBorcKalem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HopDosyaBorcKalem result = hopDosyaBorcKalemService.save(hopDosyaBorcKalem);
        return ResponseEntity.created(new URI("/api/hop-dosya-borc-kalems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hop-dosya-borc-kalems : Updates an existing hopDosyaBorcKalem.
     *
     * @param hopDosyaBorcKalem the hopDosyaBorcKalem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hopDosyaBorcKalem,
     * or with status 400 (Bad Request) if the hopDosyaBorcKalem is not valid,
     * or with status 500 (Internal Server Error) if the hopDosyaBorcKalem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hop-dosya-borc-kalems")
    @Timed
    public ResponseEntity<HopDosyaBorcKalem> updateHopDosyaBorcKalem(@RequestBody HopDosyaBorcKalem hopDosyaBorcKalem) throws URISyntaxException {
        log.debug("REST request to update HopDosyaBorcKalem : {}", hopDosyaBorcKalem);
        if (hopDosyaBorcKalem.getId() == null) {
            return createHopDosyaBorcKalem(hopDosyaBorcKalem);
        }
        HopDosyaBorcKalem result = hopDosyaBorcKalemService.save(hopDosyaBorcKalem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hopDosyaBorcKalem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hop-dosya-borc-kalems : get all the hopDosyaBorcKalems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hopDosyaBorcKalems in body
     */
    @GetMapping("/hop-dosya-borc-kalems")
    @Timed
    public List<HopDosyaBorcKalem> getAllHopDosyaBorcKalems() {
        log.debug("REST request to get all HopDosyaBorcKalems");
        return hopDosyaBorcKalemService.findAll();
        }

    /**
     * GET  /hop-dosya-borc-kalems/:id : get the "id" hopDosyaBorcKalem.
     *
     * @param id the id of the hopDosyaBorcKalem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hopDosyaBorcKalem, or with status 404 (Not Found)
     */
    @GetMapping("/hop-dosya-borc-kalems/{id}")
    @Timed
    public ResponseEntity<HopDosyaBorcKalem> getHopDosyaBorcKalem(@PathVariable Long id) {
        log.debug("REST request to get HopDosyaBorcKalem : {}", id);
        HopDosyaBorcKalem hopDosyaBorcKalem = hopDosyaBorcKalemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hopDosyaBorcKalem));
    }

    /**
     * DELETE  /hop-dosya-borc-kalems/:id : delete the "id" hopDosyaBorcKalem.
     *
     * @param id the id of the hopDosyaBorcKalem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hop-dosya-borc-kalems/{id}")
    @Timed
    public ResponseEntity<Void> deleteHopDosyaBorcKalem(@PathVariable Long id) {
        log.debug("REST request to delete HopDosyaBorcKalem : {}", id);
        hopDosyaBorcKalemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hop-dosya-borc-kalems?query=:query : search for the hopDosyaBorcKalem corresponding
     * to the query.
     *
     * @param query the query of the hopDosyaBorcKalem search
     * @return the result of the search
     */
    @GetMapping("/_search/hop-dosya-borc-kalems")
    @Timed
    public List<HopDosyaBorcKalem> searchHopDosyaBorcKalems(@RequestParam String query) {
        log.debug("REST request to search HopDosyaBorcKalems for query {}", query);
        return hopDosyaBorcKalemService.search(query);
    }

}
