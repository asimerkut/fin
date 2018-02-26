package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.MasrafTipi;
import com.er.fin.service.MasrafTipiService;
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
 * REST controller for managing MasrafTipi.
 */
@RestController
@RequestMapping("/api")
public class MasrafTipiResource {

    private final Logger log = LoggerFactory.getLogger(MasrafTipiResource.class);

    private static final String ENTITY_NAME = "masrafTipi";

    private final MasrafTipiService masrafTipiService;

    public MasrafTipiResource(MasrafTipiService masrafTipiService) {
        this.masrafTipiService = masrafTipiService;
    }

    /**
     * POST  /masraf-tipis : Create a new masrafTipi.
     *
     * @param masrafTipi the masrafTipi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new masrafTipi, or with status 400 (Bad Request) if the masrafTipi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/masraf-tipis")
    @Timed
    public ResponseEntity<MasrafTipi> createMasrafTipi(@RequestBody MasrafTipi masrafTipi) throws URISyntaxException {
        log.debug("REST request to save MasrafTipi : {}", masrafTipi);
        if (masrafTipi.getId() != null) {
            throw new BadRequestAlertException("A new masrafTipi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MasrafTipi result = masrafTipiService.save(masrafTipi);
        return ResponseEntity.created(new URI("/api/masraf-tipis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /masraf-tipis : Updates an existing masrafTipi.
     *
     * @param masrafTipi the masrafTipi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated masrafTipi,
     * or with status 400 (Bad Request) if the masrafTipi is not valid,
     * or with status 500 (Internal Server Error) if the masrafTipi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/masraf-tipis")
    @Timed
    public ResponseEntity<MasrafTipi> updateMasrafTipi(@RequestBody MasrafTipi masrafTipi) throws URISyntaxException {
        log.debug("REST request to update MasrafTipi : {}", masrafTipi);
        if (masrafTipi.getId() == null) {
            return createMasrafTipi(masrafTipi);
        }
        MasrafTipi result = masrafTipiService.save(masrafTipi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, masrafTipi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /masraf-tipis : get all the masrafTipis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of masrafTipis in body
     */
    @GetMapping("/masraf-tipis")
    @Timed
    public ResponseEntity<List<MasrafTipi>> getAllMasrafTipis(Pageable pageable) {
        log.debug("REST request to get a page of MasrafTipis");
        Page<MasrafTipi> page = masrafTipiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/masraf-tipis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /masraf-tipis/:id : get the "id" masrafTipi.
     *
     * @param id the id of the masrafTipi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the masrafTipi, or with status 404 (Not Found)
     */
    @GetMapping("/masraf-tipis/{id}")
    @Timed
    public ResponseEntity<MasrafTipi> getMasrafTipi(@PathVariable Long id) {
        log.debug("REST request to get MasrafTipi : {}", id);
        MasrafTipi masrafTipi = masrafTipiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(masrafTipi));
    }

    /**
     * DELETE  /masraf-tipis/:id : delete the "id" masrafTipi.
     *
     * @param id the id of the masrafTipi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/masraf-tipis/{id}")
    @Timed
    public ResponseEntity<Void> deleteMasrafTipi(@PathVariable Long id) {
        log.debug("REST request to delete MasrafTipi : {}", id);
        masrafTipiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/masraf-tipis?query=:query : search for the masrafTipi corresponding
     * to the query.
     *
     * @param query the query of the masrafTipi search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/masraf-tipis")
    @Timed
    public ResponseEntity<List<MasrafTipi>> searchMasrafTipis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MasrafTipis for query {}", query);
        Page<MasrafTipi> page = masrafTipiService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/masraf-tipis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
