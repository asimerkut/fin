package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.BorcKalem;
import com.er.fin.service.BorcKalemService;
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
 * REST controller for managing BorcKalem.
 */
@RestController
@RequestMapping("/api")
public class BorcKalemResource {

    private final Logger log = LoggerFactory.getLogger(BorcKalemResource.class);

    private static final String ENTITY_NAME = "borcKalem";

    private final BorcKalemService borcKalemService;

    public BorcKalemResource(BorcKalemService borcKalemService) {
        this.borcKalemService = borcKalemService;
    }

    /**
     * POST  /borc-kalems : Create a new borcKalem.
     *
     * @param borcKalem the borcKalem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new borcKalem, or with status 400 (Bad Request) if the borcKalem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/borc-kalems")
    @Timed
    public ResponseEntity<BorcKalem> createBorcKalem(@RequestBody BorcKalem borcKalem) throws URISyntaxException {
        log.debug("REST request to save BorcKalem : {}", borcKalem);
        if (borcKalem.getId() != null) {
            throw new BadRequestAlertException("A new borcKalem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BorcKalem result = borcKalemService.save(borcKalem);
        return ResponseEntity.created(new URI("/api/borc-kalems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /borc-kalems : Updates an existing borcKalem.
     *
     * @param borcKalem the borcKalem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated borcKalem,
     * or with status 400 (Bad Request) if the borcKalem is not valid,
     * or with status 500 (Internal Server Error) if the borcKalem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/borc-kalems")
    @Timed
    public ResponseEntity<BorcKalem> updateBorcKalem(@RequestBody BorcKalem borcKalem) throws URISyntaxException {
        log.debug("REST request to update BorcKalem : {}", borcKalem);
        if (borcKalem.getId() == null) {
            return createBorcKalem(borcKalem);
        }
        BorcKalem result = borcKalemService.save(borcKalem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, borcKalem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /borc-kalems : get all the borcKalems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of borcKalems in body
     */
    @GetMapping("/borc-kalems")
    @Timed
    public ResponseEntity<List<BorcKalem>> getAllBorcKalems(Pageable pageable) {
        log.debug("REST request to get a page of BorcKalems");
        Page<BorcKalem> page = borcKalemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/borc-kalems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /borc-kalems/:id : get the "id" borcKalem.
     *
     * @param id the id of the borcKalem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the borcKalem, or with status 404 (Not Found)
     */
    @GetMapping("/borc-kalems/{id}")
    @Timed
    public ResponseEntity<BorcKalem> getBorcKalem(@PathVariable Long id) {
        log.debug("REST request to get BorcKalem : {}", id);
        BorcKalem borcKalem = borcKalemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(borcKalem));
    }

    /**
     * DELETE  /borc-kalems/:id : delete the "id" borcKalem.
     *
     * @param id the id of the borcKalem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/borc-kalems/{id}")
    @Timed
    public ResponseEntity<Void> deleteBorcKalem(@PathVariable Long id) {
        log.debug("REST request to delete BorcKalem : {}", id);
        borcKalemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/borc-kalems?query=:query : search for the borcKalem corresponding
     * to the query.
     *
     * @param query the query of the borcKalem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/borc-kalems")
    @Timed
    public ResponseEntity<List<BorcKalem>> searchBorcKalems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BorcKalems for query {}", query);
        Page<BorcKalem> page = borcKalemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/borc-kalems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
