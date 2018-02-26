package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.Borc;
import com.er.fin.service.BorcService;
import com.er.fin.web.rest.errors.BadRequestAlertException;
import com.er.fin.web.rest.util.HeaderUtil;
import com.er.fin.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Borc.
 */
@RestController
@RequestMapping("/api")
public class BorcResource {

    private final Logger log = LoggerFactory.getLogger(BorcResource.class);

    private static final String ENTITY_NAME = "borc";

    private final BorcService borcService;

    public BorcResource(BorcService borcService) {
        this.borcService = borcService;
    }

    /**
     * POST  /borcs : Create a new borc.
     *
     * @param borc the borc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new borc, or with status 400 (Bad Request) if the borc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/borcs")
    @Timed
    public ResponseEntity<Borc> createBorc(@RequestBody Borc borc) throws URISyntaxException {
        log.debug("REST request to save Borc : {}", borc);
        if (borc.getId() != null) {
            throw new BadRequestAlertException("A new borc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Borc result = borcService.save(borc);
        return ResponseEntity.created(new URI("/api/borcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /borcs : Updates an existing borc.
     *
     * @param borc the borc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated borc,
     * or with status 400 (Bad Request) if the borc is not valid,
     * or with status 500 (Internal Server Error) if the borc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/borcs")
    @Timed
    public ResponseEntity<Borc> updateBorc(@RequestBody Borc borc) throws URISyntaxException {
        log.debug("REST request to update Borc : {}", borc);
        if (borc.getId() == null) {
            return createBorc(borc);
        }
        Borc result = borcService.save(borc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, borc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /borcs : get all the borcs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of borcs in body
     */
    @GetMapping("/borcs")
    @Timed
    public ResponseEntity<List<Borc>> getAllBorcs(Pageable pageable) {
        log.debug("REST request to get a page of Borcs");
        Page<Borc> page = borcService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/borcs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /borcs/:id : get the "id" borc.
     *
     * @param id the id of the borc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the borc, or with status 404 (Not Found)
     */
    @GetMapping("/borcs/{id}")
    @Timed
    public ResponseEntity<Borc> getBorc(@PathVariable Long id) {
        log.debug("REST request to get Borc : {}", id);
        Borc borc = borcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(borc));
    }

    /**
     * DELETE  /borcs/:id : delete the "id" borc.
     *
     * @param id the id of the borc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/borcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBorc(@PathVariable Long id) {
        log.debug("REST request to delete Borc : {}", id);
        borcService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/borcs?query=:query : search for the borc corresponding
     * to the query.
     *
     * @param query the query of the borc search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/borcs")
    @Timed
    public ResponseEntity<List<Borc>> searchBorcs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Borcs for query {}", query);
        Page<Borc> page = borcService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/borcs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
