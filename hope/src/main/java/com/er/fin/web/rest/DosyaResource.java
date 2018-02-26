package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.Dosya;
import com.er.fin.service.DosyaService;
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
 * REST controller for managing Dosya.
 */
@RestController
@RequestMapping("/api")
public class DosyaResource {

    private final Logger log = LoggerFactory.getLogger(DosyaResource.class);

    private static final String ENTITY_NAME = "dosya";

    private final DosyaService dosyaService;

    public DosyaResource(DosyaService dosyaService) {
        this.dosyaService = dosyaService;
    }

    /**
     * POST  /dosyas : Create a new dosya.
     *
     * @param dosya the dosya to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dosya, or with status 400 (Bad Request) if the dosya has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dosyas")
    @Timed
    public ResponseEntity<Dosya> createDosya(@RequestBody Dosya dosya) throws URISyntaxException {
        log.debug("REST request to save Dosya : {}", dosya);
        if (dosya.getId() != null) {
            throw new BadRequestAlertException("A new dosya cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dosya result = dosyaService.save(dosya);
        return ResponseEntity.created(new URI("/api/dosyas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dosyas : Updates an existing dosya.
     *
     * @param dosya the dosya to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dosya,
     * or with status 400 (Bad Request) if the dosya is not valid,
     * or with status 500 (Internal Server Error) if the dosya couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dosyas")
    @Timed
    public ResponseEntity<Dosya> updateDosya(@RequestBody Dosya dosya) throws URISyntaxException {
        log.debug("REST request to update Dosya : {}", dosya);
        if (dosya.getId() == null) {
            return createDosya(dosya);
        }
        Dosya result = dosyaService.save(dosya);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dosya.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dosyas : get all the dosyas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dosyas in body
     */
    @GetMapping("/dosyas")
    @Timed
    public ResponseEntity<List<Dosya>> getAllDosyas(Pageable pageable) {
        log.debug("REST request to get a page of Dosyas");
        Page<Dosya> page = dosyaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dosyas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dosyas/:id : get the "id" dosya.
     *
     * @param id the id of the dosya to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dosya, or with status 404 (Not Found)
     */
    @GetMapping("/dosyas/{id}")
    @Timed
    public ResponseEntity<Dosya> getDosya(@PathVariable Long id) {
        log.debug("REST request to get Dosya : {}", id);
        Dosya dosya = dosyaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dosya));
    }

    /**
     * DELETE  /dosyas/:id : delete the "id" dosya.
     *
     * @param id the id of the dosya to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dosyas/{id}")
    @Timed
    public ResponseEntity<Void> deleteDosya(@PathVariable Long id) {
        log.debug("REST request to delete Dosya : {}", id);
        dosyaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dosyas?query=:query : search for the dosya corresponding
     * to the query.
     *
     * @param query the query of the dosya search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dosyas")
    @Timed
    public ResponseEntity<List<Dosya>> searchDosyas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Dosyas for query {}", query);
        Page<Dosya> page = dosyaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dosyas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
