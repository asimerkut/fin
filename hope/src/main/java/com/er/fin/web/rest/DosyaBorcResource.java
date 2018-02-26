package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DosyaBorc;
import com.er.fin.service.DosyaBorcService;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DosyaBorc.
 */
@RestController
@RequestMapping("/api")
public class DosyaBorcResource {

    private final Logger log = LoggerFactory.getLogger(DosyaBorcResource.class);

    private static final String ENTITY_NAME = "dosyaBorc";

    private final DosyaBorcService dosyaBorcService;

    public DosyaBorcResource(DosyaBorcService dosyaBorcService) {
        this.dosyaBorcService = dosyaBorcService;
    }

    /**
     * POST  /dosya-borcs : Create a new dosyaBorc.
     *
     * @param dosyaBorc the dosyaBorc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dosyaBorc, or with status 400 (Bad Request) if the dosyaBorc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dosya-borcs")
    @Timed
    public ResponseEntity<DosyaBorc> createDosyaBorc(@RequestBody DosyaBorc dosyaBorc) throws URISyntaxException {
        log.debug("REST request to save DosyaBorc : {}", dosyaBorc);
        if (dosyaBorc.getId() != null) {
            throw new BadRequestAlertException("A new dosyaBorc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DosyaBorc result = dosyaBorcService.save(dosyaBorc);
        return ResponseEntity.created(new URI("/api/dosya-borcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dosya-borcs : Updates an existing dosyaBorc.
     *
     * @param dosyaBorc the dosyaBorc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dosyaBorc,
     * or with status 400 (Bad Request) if the dosyaBorc is not valid,
     * or with status 500 (Internal Server Error) if the dosyaBorc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dosya-borcs")
    @Timed
    public ResponseEntity<DosyaBorc> updateDosyaBorc(@RequestBody DosyaBorc dosyaBorc) throws URISyntaxException {
        log.debug("REST request to update DosyaBorc : {}", dosyaBorc);
        if (dosyaBorc.getId() == null) {
            return createDosyaBorc(dosyaBorc);
        }
        DosyaBorc result = dosyaBorcService.save(dosyaBorc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dosyaBorc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dosya-borcs : get all the dosyaBorcs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dosyaBorcs in body
     */
    @GetMapping("/dosya-borcs")
    @Timed
    public ResponseEntity<List<DosyaBorc>> getAllDosyaBorcs(Pageable pageable) {
        log.debug("REST request to get a page of DosyaBorcs");
        Page<DosyaBorc> page = dosyaBorcService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dosya-borcs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dosya-borcs/:id : get the "id" dosyaBorc.
     *
     * @param id the id of the dosyaBorc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dosyaBorc, or with status 404 (Not Found)
     */
    @GetMapping("/dosya-borcs/{id}")
    @Timed
    public ResponseEntity<DosyaBorc> getDosyaBorc(@PathVariable Long id) {
        log.debug("REST request to get DosyaBorc : {}", id);
        DosyaBorc dosyaBorc = dosyaBorcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dosyaBorc));
    }

    /**
     * DELETE  /dosya-borcs/:id : delete the "id" dosyaBorc.
     *
     * @param id the id of the dosyaBorc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dosya-borcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDosyaBorc(@PathVariable Long id) {
        log.debug("REST request to delete DosyaBorc : {}", id);
        dosyaBorcService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dosya-borcs?query=:query : search for the dosyaBorc corresponding
     * to the query.
     *
     * @param query the query of the dosyaBorc search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dosya-borcs")
    @Timed
    public ResponseEntity<List<DosyaBorc>> searchDosyaBorcs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DosyaBorcs for query {}", query);
        Page<DosyaBorc> page = dosyaBorcService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dosya-borcs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
