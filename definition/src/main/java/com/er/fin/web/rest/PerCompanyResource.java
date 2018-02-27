package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.PerCompany;
import com.er.fin.service.PerCompanyService;
import com.er.fin.web.rest.errors.BadRequestAlertException;
import com.er.fin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PerCompany.
 */
@RestController
@RequestMapping("/api")
public class PerCompanyResource {

    private final Logger log = LoggerFactory.getLogger(PerCompanyResource.class);

    private static final String ENTITY_NAME = "perCompany";

    private final PerCompanyService perCompanyService;

    public PerCompanyResource(PerCompanyService perCompanyService) {
        this.perCompanyService = perCompanyService;
    }

    /**
     * POST  /per-companies : Create a new perCompany.
     *
     * @param perCompany the perCompany to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perCompany, or with status 400 (Bad Request) if the perCompany has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/per-companies")
    @Timed
    public ResponseEntity<PerCompany> createPerCompany(@Valid @RequestBody PerCompany perCompany) throws URISyntaxException {
        log.debug("REST request to save PerCompany : {}", perCompany);
        if (perCompany.getId() != null) {
            throw new BadRequestAlertException("A new perCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerCompany result = perCompanyService.save(perCompany);
        return ResponseEntity.created(new URI("/api/per-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /per-companies : Updates an existing perCompany.
     *
     * @param perCompany the perCompany to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perCompany,
     * or with status 400 (Bad Request) if the perCompany is not valid,
     * or with status 500 (Internal Server Error) if the perCompany couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/per-companies")
    @Timed
    public ResponseEntity<PerCompany> updatePerCompany(@Valid @RequestBody PerCompany perCompany) throws URISyntaxException {
        log.debug("REST request to update PerCompany : {}", perCompany);
        if (perCompany.getId() == null) {
            return createPerCompany(perCompany);
        }
        PerCompany result = perCompanyService.save(perCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /per-companies : get all the perCompanies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of perCompanies in body
     */
    @GetMapping("/per-companies")
    @Timed
    public List<PerCompany> getAllPerCompanies() {
        log.debug("REST request to get all PerCompanies");
        return perCompanyService.findAll();
        }

    /**
     * GET  /per-companies/:id : get the "id" perCompany.
     *
     * @param id the id of the perCompany to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perCompany, or with status 404 (Not Found)
     */
    @GetMapping("/per-companies/{id}")
    @Timed
    public ResponseEntity<PerCompany> getPerCompany(@PathVariable Long id) {
        log.debug("REST request to get PerCompany : {}", id);
        PerCompany perCompany = perCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perCompany));
    }

    /**
     * DELETE  /per-companies/:id : delete the "id" perCompany.
     *
     * @param id the id of the perCompany to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/per-companies/{id}")
    @Timed
    public ResponseEntity<Void> deletePerCompany(@PathVariable Long id) {
        log.debug("REST request to delete PerCompany : {}", id);
        perCompanyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/per-companies?query=:query : search for the perCompany corresponding
     * to the query.
     *
     * @param query the query of the perCompany search
     * @return the result of the search
     */
    @GetMapping("/_search/per-companies")
    @Timed
    public List<PerCompany> searchPerCompanies(@RequestParam String query) {
        log.debug("REST request to search PerCompanies for query {}", query);
        return perCompanyService.search(query);
    }

}
