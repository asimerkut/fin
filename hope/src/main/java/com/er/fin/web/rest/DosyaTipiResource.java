package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DosyaTipi;
import com.er.fin.service.DosyaTipiService;
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
 * REST controller for managing DosyaTipi.
 */
@RestController
@RequestMapping("/api")
public class DosyaTipiResource {

    private final Logger log = LoggerFactory.getLogger(DosyaTipiResource.class);

    private static final String ENTITY_NAME = "dosyaTipi";

    private final DosyaTipiService dosyaTipiService;

    public DosyaTipiResource(DosyaTipiService dosyaTipiService) {
        this.dosyaTipiService = dosyaTipiService;
    }

    /**
     * POST  /dosya-tipis : Create a new dosyaTipi.
     *
     * @param dosyaTipi the dosyaTipi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dosyaTipi, or with status 400 (Bad Request) if the dosyaTipi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dosya-tipis")
    @Timed
    public ResponseEntity<DosyaTipi> createDosyaTipi(@RequestBody DosyaTipi dosyaTipi) throws URISyntaxException {
        log.debug("REST request to save DosyaTipi : {}", dosyaTipi);
        if (dosyaTipi.getId() != null) {
            throw new BadRequestAlertException("A new dosyaTipi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DosyaTipi result = dosyaTipiService.save(dosyaTipi);
        return ResponseEntity.created(new URI("/api/dosya-tipis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dosya-tipis : Updates an existing dosyaTipi.
     *
     * @param dosyaTipi the dosyaTipi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dosyaTipi,
     * or with status 400 (Bad Request) if the dosyaTipi is not valid,
     * or with status 500 (Internal Server Error) if the dosyaTipi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dosya-tipis")
    @Timed
    public ResponseEntity<DosyaTipi> updateDosyaTipi(@RequestBody DosyaTipi dosyaTipi) throws URISyntaxException {
        log.debug("REST request to update DosyaTipi : {}", dosyaTipi);
        if (dosyaTipi.getId() == null) {
            return createDosyaTipi(dosyaTipi);
        }
        DosyaTipi result = dosyaTipiService.save(dosyaTipi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dosyaTipi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dosya-tipis : get all the dosyaTipis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dosyaTipis in body
     */
    @GetMapping("/dosya-tipis")
    @Timed
    public ResponseEntity<List<DosyaTipi>> getAllDosyaTipis(Pageable pageable) {
        log.debug("REST request to get a page of DosyaTipis");
        Page<DosyaTipi> page = dosyaTipiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dosya-tipis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dosya-tipis/:id : get the "id" dosyaTipi.
     *
     * @param id the id of the dosyaTipi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dosyaTipi, or with status 404 (Not Found)
     */
    @GetMapping("/dosya-tipis/{id}")
    @Timed
    public ResponseEntity<DosyaTipi> getDosyaTipi(@PathVariable Long id) {
        log.debug("REST request to get DosyaTipi : {}", id);
        DosyaTipi dosyaTipi = dosyaTipiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dosyaTipi));
    }

    /**
     * DELETE  /dosya-tipis/:id : delete the "id" dosyaTipi.
     *
     * @param id the id of the dosyaTipi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dosya-tipis/{id}")
    @Timed
    public ResponseEntity<Void> deleteDosyaTipi(@PathVariable Long id) {
        log.debug("REST request to delete DosyaTipi : {}", id);
        dosyaTipiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dosya-tipis?query=:query : search for the dosyaTipi corresponding
     * to the query.
     *
     * @param query the query of the dosyaTipi search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dosya-tipis")
    @Timed
    public ResponseEntity<List<DosyaTipi>> searchDosyaTipis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DosyaTipis for query {}", query);
        Page<DosyaTipi> page = dosyaTipiService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dosya-tipis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
