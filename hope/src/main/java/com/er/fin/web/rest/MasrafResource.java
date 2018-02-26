package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.Masraf;
import com.er.fin.service.MasrafService;
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
 * REST controller for managing Masraf.
 */
@RestController
@RequestMapping("/api")
public class MasrafResource {

    private final Logger log = LoggerFactory.getLogger(MasrafResource.class);

    private static final String ENTITY_NAME = "masraf";

    private final MasrafService masrafService;

    public MasrafResource(MasrafService masrafService) {
        this.masrafService = masrafService;
    }

    /**
     * POST  /masrafs : Create a new masraf.
     *
     * @param masraf the masraf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new masraf, or with status 400 (Bad Request) if the masraf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/masrafs")
    @Timed
    public ResponseEntity<Masraf> createMasraf(@RequestBody Masraf masraf) throws URISyntaxException {
        log.debug("REST request to save Masraf : {}", masraf);
        if (masraf.getId() != null) {
            throw new BadRequestAlertException("A new masraf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Masraf result = masrafService.save(masraf);
        return ResponseEntity.created(new URI("/api/masrafs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /masrafs : Updates an existing masraf.
     *
     * @param masraf the masraf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated masraf,
     * or with status 400 (Bad Request) if the masraf is not valid,
     * or with status 500 (Internal Server Error) if the masraf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/masrafs")
    @Timed
    public ResponseEntity<Masraf> updateMasraf(@RequestBody Masraf masraf) throws URISyntaxException {
        log.debug("REST request to update Masraf : {}", masraf);
        if (masraf.getId() == null) {
            return createMasraf(masraf);
        }
        Masraf result = masrafService.save(masraf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, masraf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /masrafs : get all the masrafs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of masrafs in body
     */
    @GetMapping("/masrafs")
    @Timed
    public ResponseEntity<List<Masraf>> getAllMasrafs(Pageable pageable) {
        log.debug("REST request to get a page of Masrafs");
        Page<Masraf> page = masrafService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/masrafs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /masrafs/:id : get the "id" masraf.
     *
     * @param id the id of the masraf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the masraf, or with status 404 (Not Found)
     */
    @GetMapping("/masrafs/{id}")
    @Timed
    public ResponseEntity<Masraf> getMasraf(@PathVariable Long id) {
        log.debug("REST request to get Masraf : {}", id);
        Masraf masraf = masrafService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(masraf));
    }

    /**
     * DELETE  /masrafs/:id : delete the "id" masraf.
     *
     * @param id the id of the masraf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/masrafs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMasraf(@PathVariable Long id) {
        log.debug("REST request to delete Masraf : {}", id);
        masrafService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/masrafs?query=:query : search for the masraf corresponding
     * to the query.
     *
     * @param query the query of the masraf search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/masrafs")
    @Timed
    public ResponseEntity<List<Masraf>> searchMasrafs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Masrafs for query {}", query);
        Page<Masraf> page = masrafService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/masrafs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
