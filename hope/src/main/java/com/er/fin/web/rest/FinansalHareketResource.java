package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.FinansalHareket;
import com.er.fin.service.FinansalHareketService;
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
 * REST controller for managing FinansalHareket.
 */
@RestController
@RequestMapping("/api")
public class FinansalHareketResource {

    private final Logger log = LoggerFactory.getLogger(FinansalHareketResource.class);

    private static final String ENTITY_NAME = "finansalHareket";

    private final FinansalHareketService finansalHareketService;

    public FinansalHareketResource(FinansalHareketService finansalHareketService) {
        this.finansalHareketService = finansalHareketService;
    }

    /**
     * POST  /finansal-harekets : Create a new finansalHareket.
     *
     * @param finansalHareket the finansalHareket to create
     * @return the ResponseEntity with status 201 (Created) and with body the new finansalHareket, or with status 400 (Bad Request) if the finansalHareket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/finansal-harekets")
    @Timed
    public ResponseEntity<FinansalHareket> createFinansalHareket(@RequestBody FinansalHareket finansalHareket) throws URISyntaxException {
        log.debug("REST request to save FinansalHareket : {}", finansalHareket);
        if (finansalHareket.getId() != null) {
            throw new BadRequestAlertException("A new finansalHareket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinansalHareket result = finansalHareketService.save(finansalHareket);
        return ResponseEntity.created(new URI("/api/finansal-harekets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /finansal-harekets : Updates an existing finansalHareket.
     *
     * @param finansalHareket the finansalHareket to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated finansalHareket,
     * or with status 400 (Bad Request) if the finansalHareket is not valid,
     * or with status 500 (Internal Server Error) if the finansalHareket couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/finansal-harekets")
    @Timed
    public ResponseEntity<FinansalHareket> updateFinansalHareket(@RequestBody FinansalHareket finansalHareket) throws URISyntaxException {
        log.debug("REST request to update FinansalHareket : {}", finansalHareket);
        if (finansalHareket.getId() == null) {
            return createFinansalHareket(finansalHareket);
        }
        FinansalHareket result = finansalHareketService.save(finansalHareket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, finansalHareket.getId().toString()))
            .body(result);
    }

    /**
     * GET  /finansal-harekets : get all the finansalHarekets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of finansalHarekets in body
     */
    @GetMapping("/finansal-harekets")
    @Timed
    public ResponseEntity<List<FinansalHareket>> getAllFinansalHarekets(Pageable pageable) {
        log.debug("REST request to get a page of FinansalHarekets");
        Page<FinansalHareket> page = finansalHareketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/finansal-harekets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /finansal-harekets/:id : get the "id" finansalHareket.
     *
     * @param id the id of the finansalHareket to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the finansalHareket, or with status 404 (Not Found)
     */
    @GetMapping("/finansal-harekets/{id}")
    @Timed
    public ResponseEntity<FinansalHareket> getFinansalHareket(@PathVariable Long id) {
        log.debug("REST request to get FinansalHareket : {}", id);
        FinansalHareket finansalHareket = finansalHareketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(finansalHareket));
    }

    /**
     * DELETE  /finansal-harekets/:id : delete the "id" finansalHareket.
     *
     * @param id the id of the finansalHareket to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/finansal-harekets/{id}")
    @Timed
    public ResponseEntity<Void> deleteFinansalHareket(@PathVariable Long id) {
        log.debug("REST request to delete FinansalHareket : {}", id);
        finansalHareketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/finansal-harekets?query=:query : search for the finansalHareket corresponding
     * to the query.
     *
     * @param query the query of the finansalHareket search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/finansal-harekets")
    @Timed
    public ResponseEntity<List<FinansalHareket>> searchFinansalHarekets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FinansalHarekets for query {}", query);
        Page<FinansalHareket> page = finansalHareketService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/finansal-harekets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
