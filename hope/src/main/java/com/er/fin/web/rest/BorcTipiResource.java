package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.BorcTipi;
import com.er.fin.service.BorcTipiService;
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
 * REST controller for managing BorcTipi.
 */
@RestController
@RequestMapping("/api")
public class BorcTipiResource {

    private final Logger log = LoggerFactory.getLogger(BorcTipiResource.class);

    private static final String ENTITY_NAME = "borcTipi";

    private final BorcTipiService borcTipiService;

    public BorcTipiResource(BorcTipiService borcTipiService) {
        this.borcTipiService = borcTipiService;
    }

    /**
     * POST  /borc-tipis : Create a new borcTipi.
     *
     * @param borcTipi the borcTipi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new borcTipi, or with status 400 (Bad Request) if the borcTipi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/borc-tipis")
    @Timed
    public ResponseEntity<BorcTipi> createBorcTipi(@RequestBody BorcTipi borcTipi) throws URISyntaxException {
        log.debug("REST request to save BorcTipi : {}", borcTipi);
        if (borcTipi.getId() != null) {
            throw new BadRequestAlertException("A new borcTipi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BorcTipi result = borcTipiService.save(borcTipi);
        return ResponseEntity.created(new URI("/api/borc-tipis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /borc-tipis : Updates an existing borcTipi.
     *
     * @param borcTipi the borcTipi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated borcTipi,
     * or with status 400 (Bad Request) if the borcTipi is not valid,
     * or with status 500 (Internal Server Error) if the borcTipi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/borc-tipis")
    @Timed
    public ResponseEntity<BorcTipi> updateBorcTipi(@RequestBody BorcTipi borcTipi) throws URISyntaxException {
        log.debug("REST request to update BorcTipi : {}", borcTipi);
        if (borcTipi.getId() == null) {
            return createBorcTipi(borcTipi);
        }
        BorcTipi result = borcTipiService.save(borcTipi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, borcTipi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /borc-tipis : get all the borcTipis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of borcTipis in body
     */
    @GetMapping("/borc-tipis")
    @Timed
    public ResponseEntity<List<BorcTipi>> getAllBorcTipis(Pageable pageable) {
        log.debug("REST request to get a page of BorcTipis");
        Page<BorcTipi> page = borcTipiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/borc-tipis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /borc-tipis/:id : get the "id" borcTipi.
     *
     * @param id the id of the borcTipi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the borcTipi, or with status 404 (Not Found)
     */
    @GetMapping("/borc-tipis/{id}")
    @Timed
    public ResponseEntity<BorcTipi> getBorcTipi(@PathVariable Long id) {
        log.debug("REST request to get BorcTipi : {}", id);
        BorcTipi borcTipi = borcTipiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(borcTipi));
    }

    /**
     * DELETE  /borc-tipis/:id : delete the "id" borcTipi.
     *
     * @param id the id of the borcTipi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/borc-tipis/{id}")
    @Timed
    public ResponseEntity<Void> deleteBorcTipi(@PathVariable Long id) {
        log.debug("REST request to delete BorcTipi : {}", id);
        borcTipiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/borc-tipis?query=:query : search for the borcTipi corresponding
     * to the query.
     *
     * @param query the query of the borcTipi search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/borc-tipis")
    @Timed
    public ResponseEntity<List<BorcTipi>> searchBorcTipis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BorcTipis for query {}", query);
        Page<BorcTipi> page = borcTipiService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/borc-tipis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
