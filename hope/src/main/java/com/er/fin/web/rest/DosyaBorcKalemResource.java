package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DosyaBorcKalem;
import com.er.fin.service.DosyaBorcKalemService;
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
 * REST controller for managing DosyaBorcKalem.
 */
@RestController
@RequestMapping("/api")
public class DosyaBorcKalemResource {

    private final Logger log = LoggerFactory.getLogger(DosyaBorcKalemResource.class);

    private static final String ENTITY_NAME = "dosyaBorcKalem";

    private final DosyaBorcKalemService dosyaBorcKalemService;

    public DosyaBorcKalemResource(DosyaBorcKalemService dosyaBorcKalemService) {
        this.dosyaBorcKalemService = dosyaBorcKalemService;
    }

    /**
     * POST  /dosya-borc-kalems : Create a new dosyaBorcKalem.
     *
     * @param dosyaBorcKalem the dosyaBorcKalem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dosyaBorcKalem, or with status 400 (Bad Request) if the dosyaBorcKalem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dosya-borc-kalems")
    @Timed
    public ResponseEntity<DosyaBorcKalem> createDosyaBorcKalem(@RequestBody DosyaBorcKalem dosyaBorcKalem) throws URISyntaxException {
        log.debug("REST request to save DosyaBorcKalem : {}", dosyaBorcKalem);
        if (dosyaBorcKalem.getId() != null) {
            throw new BadRequestAlertException("A new dosyaBorcKalem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DosyaBorcKalem result = dosyaBorcKalemService.save(dosyaBorcKalem);
        return ResponseEntity.created(new URI("/api/dosya-borc-kalems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dosya-borc-kalems : Updates an existing dosyaBorcKalem.
     *
     * @param dosyaBorcKalem the dosyaBorcKalem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dosyaBorcKalem,
     * or with status 400 (Bad Request) if the dosyaBorcKalem is not valid,
     * or with status 500 (Internal Server Error) if the dosyaBorcKalem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dosya-borc-kalems")
    @Timed
    public ResponseEntity<DosyaBorcKalem> updateDosyaBorcKalem(@RequestBody DosyaBorcKalem dosyaBorcKalem) throws URISyntaxException {
        log.debug("REST request to update DosyaBorcKalem : {}", dosyaBorcKalem);
        if (dosyaBorcKalem.getId() == null) {
            return createDosyaBorcKalem(dosyaBorcKalem);
        }
        DosyaBorcKalem result = dosyaBorcKalemService.save(dosyaBorcKalem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dosyaBorcKalem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dosya-borc-kalems : get all the dosyaBorcKalems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dosyaBorcKalems in body
     */
    @GetMapping("/dosya-borc-kalems")
    @Timed
    public ResponseEntity<List<DosyaBorcKalem>> getAllDosyaBorcKalems(Pageable pageable) {
        log.debug("REST request to get a page of DosyaBorcKalems");
        Page<DosyaBorcKalem> page = dosyaBorcKalemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dosya-borc-kalems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dosya-borc-kalems/:id : get the "id" dosyaBorcKalem.
     *
     * @param id the id of the dosyaBorcKalem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dosyaBorcKalem, or with status 404 (Not Found)
     */
    @GetMapping("/dosya-borc-kalems/{id}")
    @Timed
    public ResponseEntity<DosyaBorcKalem> getDosyaBorcKalem(@PathVariable Long id) {
        log.debug("REST request to get DosyaBorcKalem : {}", id);
        DosyaBorcKalem dosyaBorcKalem = dosyaBorcKalemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dosyaBorcKalem));
    }

    /**
     * DELETE  /dosya-borc-kalems/:id : delete the "id" dosyaBorcKalem.
     *
     * @param id the id of the dosyaBorcKalem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dosya-borc-kalems/{id}")
    @Timed
    public ResponseEntity<Void> deleteDosyaBorcKalem(@PathVariable Long id) {
        log.debug("REST request to delete DosyaBorcKalem : {}", id);
        dosyaBorcKalemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dosya-borc-kalems?query=:query : search for the dosyaBorcKalem corresponding
     * to the query.
     *
     * @param query the query of the dosyaBorcKalem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dosya-borc-kalems")
    @Timed
    public ResponseEntity<List<DosyaBorcKalem>> searchDosyaBorcKalems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DosyaBorcKalems for query {}", query);
        Page<DosyaBorcKalem> page = dosyaBorcKalemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dosya-borc-kalems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
