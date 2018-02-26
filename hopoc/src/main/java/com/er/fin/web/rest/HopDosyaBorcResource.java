package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.HopDosyaBorc;
import com.er.fin.service.HopDosyaBorcService;
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
 * REST controller for managing HopDosyaBorc.
 */
@RestController
@RequestMapping("/api")
public class HopDosyaBorcResource {

    private final Logger log = LoggerFactory.getLogger(HopDosyaBorcResource.class);

    private static final String ENTITY_NAME = "hopDosyaBorc";

    private final HopDosyaBorcService hopDosyaBorcService;

    public HopDosyaBorcResource(HopDosyaBorcService hopDosyaBorcService) {
        this.hopDosyaBorcService = hopDosyaBorcService;
    }

    /**
     * POST  /hop-dosya-borcs : Create a new hopDosyaBorc.
     *
     * @param hopDosyaBorc the hopDosyaBorc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hopDosyaBorc, or with status 400 (Bad Request) if the hopDosyaBorc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hop-dosya-borcs")
    @Timed
    public ResponseEntity<HopDosyaBorc> createHopDosyaBorc(@RequestBody HopDosyaBorc hopDosyaBorc) throws URISyntaxException {
        log.debug("REST request to save HopDosyaBorc : {}", hopDosyaBorc);
        if (hopDosyaBorc.getId() != null) {
            throw new BadRequestAlertException("A new hopDosyaBorc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HopDosyaBorc result = hopDosyaBorcService.save(hopDosyaBorc);
        return ResponseEntity.created(new URI("/api/hop-dosya-borcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hop-dosya-borcs : Updates an existing hopDosyaBorc.
     *
     * @param hopDosyaBorc the hopDosyaBorc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hopDosyaBorc,
     * or with status 400 (Bad Request) if the hopDosyaBorc is not valid,
     * or with status 500 (Internal Server Error) if the hopDosyaBorc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hop-dosya-borcs")
    @Timed
    public ResponseEntity<HopDosyaBorc> updateHopDosyaBorc(@RequestBody HopDosyaBorc hopDosyaBorc) throws URISyntaxException {
        log.debug("REST request to update HopDosyaBorc : {}", hopDosyaBorc);
        if (hopDosyaBorc.getId() == null) {
            return createHopDosyaBorc(hopDosyaBorc);
        }
        HopDosyaBorc result = hopDosyaBorcService.save(hopDosyaBorc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hopDosyaBorc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hop-dosya-borcs : get all the hopDosyaBorcs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hopDosyaBorcs in body
     */
    @GetMapping("/hop-dosya-borcs")
    @Timed
    public List<HopDosyaBorc> getAllHopDosyaBorcs() {
        log.debug("REST request to get all HopDosyaBorcs");
        return hopDosyaBorcService.findAll();
        }

    /**
     * GET  /hop-dosya-borcs/:id : get the "id" hopDosyaBorc.
     *
     * @param id the id of the hopDosyaBorc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hopDosyaBorc, or with status 404 (Not Found)
     */
    @GetMapping("/hop-dosya-borcs/{id}")
    @Timed
    public ResponseEntity<HopDosyaBorc> getHopDosyaBorc(@PathVariable Long id) {
        log.debug("REST request to get HopDosyaBorc : {}", id);
        HopDosyaBorc hopDosyaBorc = hopDosyaBorcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hopDosyaBorc));
    }

    /**
     * DELETE  /hop-dosya-borcs/:id : delete the "id" hopDosyaBorc.
     *
     * @param id the id of the hopDosyaBorc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hop-dosya-borcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteHopDosyaBorc(@PathVariable Long id) {
        log.debug("REST request to delete HopDosyaBorc : {}", id);
        hopDosyaBorcService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hop-dosya-borcs?query=:query : search for the hopDosyaBorc corresponding
     * to the query.
     *
     * @param query the query of the hopDosyaBorc search
     * @return the result of the search
     */
    @GetMapping("/_search/hop-dosya-borcs")
    @Timed
    public List<HopDosyaBorc> searchHopDosyaBorcs(@RequestParam String query) {
        log.debug("REST request to search HopDosyaBorcs for query {}", query);
        return hopDosyaBorcService.search(query);
    }

}
