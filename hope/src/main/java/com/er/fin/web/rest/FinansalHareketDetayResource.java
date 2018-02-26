package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.FinansalHareketDetay;
import com.er.fin.service.FinansalHareketDetayService;
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
 * REST controller for managing FinansalHareketDetay.
 */
@RestController
@RequestMapping("/api")
public class FinansalHareketDetayResource {

    private final Logger log = LoggerFactory.getLogger(FinansalHareketDetayResource.class);

    private static final String ENTITY_NAME = "finansalHareketDetay";

    private final FinansalHareketDetayService finansalHareketDetayService;

    public FinansalHareketDetayResource(FinansalHareketDetayService finansalHareketDetayService) {
        this.finansalHareketDetayService = finansalHareketDetayService;
    }

    /**
     * POST  /finansal-hareket-detays : Create a new finansalHareketDetay.
     *
     * @param finansalHareketDetay the finansalHareketDetay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new finansalHareketDetay, or with status 400 (Bad Request) if the finansalHareketDetay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/finansal-hareket-detays")
    @Timed
    public ResponseEntity<FinansalHareketDetay> createFinansalHareketDetay(@RequestBody FinansalHareketDetay finansalHareketDetay) throws URISyntaxException {
        log.debug("REST request to save FinansalHareketDetay : {}", finansalHareketDetay);
        if (finansalHareketDetay.getId() != null) {
            throw new BadRequestAlertException("A new finansalHareketDetay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinansalHareketDetay result = finansalHareketDetayService.save(finansalHareketDetay);
        return ResponseEntity.created(new URI("/api/finansal-hareket-detays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /finansal-hareket-detays : Updates an existing finansalHareketDetay.
     *
     * @param finansalHareketDetay the finansalHareketDetay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated finansalHareketDetay,
     * or with status 400 (Bad Request) if the finansalHareketDetay is not valid,
     * or with status 500 (Internal Server Error) if the finansalHareketDetay couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/finansal-hareket-detays")
    @Timed
    public ResponseEntity<FinansalHareketDetay> updateFinansalHareketDetay(@RequestBody FinansalHareketDetay finansalHareketDetay) throws URISyntaxException {
        log.debug("REST request to update FinansalHareketDetay : {}", finansalHareketDetay);
        if (finansalHareketDetay.getId() == null) {
            return createFinansalHareketDetay(finansalHareketDetay);
        }
        FinansalHareketDetay result = finansalHareketDetayService.save(finansalHareketDetay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, finansalHareketDetay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /finansal-hareket-detays : get all the finansalHareketDetays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of finansalHareketDetays in body
     */
    @GetMapping("/finansal-hareket-detays")
    @Timed
    public ResponseEntity<List<FinansalHareketDetay>> getAllFinansalHareketDetays(Pageable pageable) {
        log.debug("REST request to get a page of FinansalHareketDetays");
        Page<FinansalHareketDetay> page = finansalHareketDetayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/finansal-hareket-detays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /finansal-hareket-detays/:id : get the "id" finansalHareketDetay.
     *
     * @param id the id of the finansalHareketDetay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the finansalHareketDetay, or with status 404 (Not Found)
     */
    @GetMapping("/finansal-hareket-detays/{id}")
    @Timed
    public ResponseEntity<FinansalHareketDetay> getFinansalHareketDetay(@PathVariable Long id) {
        log.debug("REST request to get FinansalHareketDetay : {}", id);
        FinansalHareketDetay finansalHareketDetay = finansalHareketDetayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(finansalHareketDetay));
    }

    /**
     * DELETE  /finansal-hareket-detays/:id : delete the "id" finansalHareketDetay.
     *
     * @param id the id of the finansalHareketDetay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/finansal-hareket-detays/{id}")
    @Timed
    public ResponseEntity<Void> deleteFinansalHareketDetay(@PathVariable Long id) {
        log.debug("REST request to delete FinansalHareketDetay : {}", id);
        finansalHareketDetayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/finansal-hareket-detays?query=:query : search for the finansalHareketDetay corresponding
     * to the query.
     *
     * @param query the query of the finansalHareketDetay search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/finansal-hareket-detays")
    @Timed
    public ResponseEntity<List<FinansalHareketDetay>> searchFinansalHareketDetays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FinansalHareketDetays for query {}", query);
        Page<FinansalHareketDetay> page = finansalHareketDetayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/finansal-hareket-detays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
