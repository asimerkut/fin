package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.IslemKodu;
import com.er.fin.service.IslemKoduService;
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
 * REST controller for managing IslemKodu.
 */
@RestController
@RequestMapping("/api")
public class IslemKoduResource {

    private final Logger log = LoggerFactory.getLogger(IslemKoduResource.class);

    private static final String ENTITY_NAME = "islemKodu";

    private final IslemKoduService islemKoduService;

    public IslemKoduResource(IslemKoduService islemKoduService) {
        this.islemKoduService = islemKoduService;
    }

    /**
     * POST  /islem-kodus : Create a new islemKodu.
     *
     * @param islemKodu the islemKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new islemKodu, or with status 400 (Bad Request) if the islemKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/islem-kodus")
    @Timed
    public ResponseEntity<IslemKodu> createIslemKodu(@RequestBody IslemKodu islemKodu) throws URISyntaxException {
        log.debug("REST request to save IslemKodu : {}", islemKodu);
        if (islemKodu.getId() != null) {
            throw new BadRequestAlertException("A new islemKodu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IslemKodu result = islemKoduService.save(islemKodu);
        return ResponseEntity.created(new URI("/api/islem-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /islem-kodus : Updates an existing islemKodu.
     *
     * @param islemKodu the islemKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated islemKodu,
     * or with status 400 (Bad Request) if the islemKodu is not valid,
     * or with status 500 (Internal Server Error) if the islemKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/islem-kodus")
    @Timed
    public ResponseEntity<IslemKodu> updateIslemKodu(@RequestBody IslemKodu islemKodu) throws URISyntaxException {
        log.debug("REST request to update IslemKodu : {}", islemKodu);
        if (islemKodu.getId() == null) {
            return createIslemKodu(islemKodu);
        }
        IslemKodu result = islemKoduService.save(islemKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, islemKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /islem-kodus : get all the islemKodus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of islemKodus in body
     */
    @GetMapping("/islem-kodus")
    @Timed
    public ResponseEntity<List<IslemKodu>> getAllIslemKodus(Pageable pageable) {
        log.debug("REST request to get a page of IslemKodus");
        Page<IslemKodu> page = islemKoduService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/islem-kodus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /islem-kodus/:id : get the "id" islemKodu.
     *
     * @param id the id of the islemKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the islemKodu, or with status 404 (Not Found)
     */
    @GetMapping("/islem-kodus/{id}")
    @Timed
    public ResponseEntity<IslemKodu> getIslemKodu(@PathVariable Long id) {
        log.debug("REST request to get IslemKodu : {}", id);
        IslemKodu islemKodu = islemKoduService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(islemKodu));
    }

    /**
     * DELETE  /islem-kodus/:id : delete the "id" islemKodu.
     *
     * @param id the id of the islemKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/islem-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteIslemKodu(@PathVariable Long id) {
        log.debug("REST request to delete IslemKodu : {}", id);
        islemKoduService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/islem-kodus?query=:query : search for the islemKodu corresponding
     * to the query.
     *
     * @param query the query of the islemKodu search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/islem-kodus")
    @Timed
    public ResponseEntity<List<IslemKodu>> searchIslemKodus(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IslemKodus for query {}", query);
        Page<IslemKodu> page = islemKoduService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/islem-kodus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
