package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.BorcGrubu;
import com.er.fin.service.BorcGrubuService;
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
 * REST controller for managing BorcGrubu.
 */
@RestController
@RequestMapping("/api")
public class BorcGrubuResource {

    private final Logger log = LoggerFactory.getLogger(BorcGrubuResource.class);

    private static final String ENTITY_NAME = "borcGrubu";

    private final BorcGrubuService borcGrubuService;

    public BorcGrubuResource(BorcGrubuService borcGrubuService) {
        this.borcGrubuService = borcGrubuService;
    }

    /**
     * POST  /borc-grubus : Create a new borcGrubu.
     *
     * @param borcGrubu the borcGrubu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new borcGrubu, or with status 400 (Bad Request) if the borcGrubu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/borc-grubus")
    @Timed
    public ResponseEntity<BorcGrubu> createBorcGrubu(@RequestBody BorcGrubu borcGrubu) throws URISyntaxException {
        log.debug("REST request to save BorcGrubu : {}", borcGrubu);
        if (borcGrubu.getId() != null) {
            throw new BadRequestAlertException("A new borcGrubu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BorcGrubu result = borcGrubuService.save(borcGrubu);
        return ResponseEntity.created(new URI("/api/borc-grubus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /borc-grubus : Updates an existing borcGrubu.
     *
     * @param borcGrubu the borcGrubu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated borcGrubu,
     * or with status 400 (Bad Request) if the borcGrubu is not valid,
     * or with status 500 (Internal Server Error) if the borcGrubu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/borc-grubus")
    @Timed
    public ResponseEntity<BorcGrubu> updateBorcGrubu(@RequestBody BorcGrubu borcGrubu) throws URISyntaxException {
        log.debug("REST request to update BorcGrubu : {}", borcGrubu);
        if (borcGrubu.getId() == null) {
            return createBorcGrubu(borcGrubu);
        }
        BorcGrubu result = borcGrubuService.save(borcGrubu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, borcGrubu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /borc-grubus : get all the borcGrubus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of borcGrubus in body
     */
    @GetMapping("/borc-grubus")
    @Timed
    public ResponseEntity<List<BorcGrubu>> getAllBorcGrubus(Pageable pageable) {
        log.debug("REST request to get a page of BorcGrubus");
        Page<BorcGrubu> page = borcGrubuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/borc-grubus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /borc-grubus/:id : get the "id" borcGrubu.
     *
     * @param id the id of the borcGrubu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the borcGrubu, or with status 404 (Not Found)
     */
    @GetMapping("/borc-grubus/{id}")
    @Timed
    public ResponseEntity<BorcGrubu> getBorcGrubu(@PathVariable Long id) {
        log.debug("REST request to get BorcGrubu : {}", id);
        BorcGrubu borcGrubu = borcGrubuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(borcGrubu));
    }

    /**
     * DELETE  /borc-grubus/:id : delete the "id" borcGrubu.
     *
     * @param id the id of the borcGrubu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/borc-grubus/{id}")
    @Timed
    public ResponseEntity<Void> deleteBorcGrubu(@PathVariable Long id) {
        log.debug("REST request to delete BorcGrubu : {}", id);
        borcGrubuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/borc-grubus?query=:query : search for the borcGrubu corresponding
     * to the query.
     *
     * @param query the query of the borcGrubu search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/borc-grubus")
    @Timed
    public ResponseEntity<List<BorcGrubu>> searchBorcGrubus(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BorcGrubus for query {}", query);
        Page<BorcGrubu> page = borcGrubuService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/borc-grubus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
