package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.PerExcuse;
import com.er.fin.service.PerExcuseService;
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
 * REST controller for managing PerExcuse.
 */
@RestController
@RequestMapping("/api")
public class PerExcuseResource {

    private final Logger log = LoggerFactory.getLogger(PerExcuseResource.class);

    private static final String ENTITY_NAME = "perExcuse";

    private final PerExcuseService perExcuseService;

    public PerExcuseResource(PerExcuseService perExcuseService) {
        this.perExcuseService = perExcuseService;
    }

    /**
     * POST  /per-excuses : Create a new perExcuse.
     *
     * @param perExcuse the perExcuse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perExcuse, or with status 400 (Bad Request) if the perExcuse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/per-excuses")
    @Timed
    public ResponseEntity<PerExcuse> createPerExcuse(@Valid @RequestBody PerExcuse perExcuse) throws URISyntaxException {
        log.debug("REST request to save PerExcuse : {}", perExcuse);
        if (perExcuse.getId() != null) {
            throw new BadRequestAlertException("A new perExcuse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerExcuse result = perExcuseService.save(perExcuse);
        return ResponseEntity.created(new URI("/api/per-excuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /per-excuses : Updates an existing perExcuse.
     *
     * @param perExcuse the perExcuse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perExcuse,
     * or with status 400 (Bad Request) if the perExcuse is not valid,
     * or with status 500 (Internal Server Error) if the perExcuse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/per-excuses")
    @Timed
    public ResponseEntity<PerExcuse> updatePerExcuse(@Valid @RequestBody PerExcuse perExcuse) throws URISyntaxException {
        log.debug("REST request to update PerExcuse : {}", perExcuse);
        if (perExcuse.getId() == null) {
            return createPerExcuse(perExcuse);
        }
        PerExcuse result = perExcuseService.save(perExcuse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perExcuse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /per-excuses : get all the perExcuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of perExcuses in body
     */
    @GetMapping("/per-excuses")
    @Timed
    public List<PerExcuse> getAllPerExcuses() {
        log.debug("REST request to get all PerExcuses");
        return perExcuseService.findAll();
        }

    /**
     * GET  /per-excuses/:id : get the "id" perExcuse.
     *
     * @param id the id of the perExcuse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perExcuse, or with status 404 (Not Found)
     */
    @GetMapping("/per-excuses/{id}")
    @Timed
    public ResponseEntity<PerExcuse> getPerExcuse(@PathVariable Long id) {
        log.debug("REST request to get PerExcuse : {}", id);
        PerExcuse perExcuse = perExcuseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perExcuse));
    }

    /**
     * DELETE  /per-excuses/:id : delete the "id" perExcuse.
     *
     * @param id the id of the perExcuse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/per-excuses/{id}")
    @Timed
    public ResponseEntity<Void> deletePerExcuse(@PathVariable Long id) {
        log.debug("REST request to delete PerExcuse : {}", id);
        perExcuseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/per-excuses?query=:query : search for the perExcuse corresponding
     * to the query.
     *
     * @param query the query of the perExcuse search
     * @return the result of the search
     */
    @GetMapping("/_search/per-excuses")
    @Timed
    public List<PerExcuse> searchPerExcuses(@RequestParam String query) {
        log.debug("REST request to search PerExcuses for query {}", query);
        return perExcuseService.search(query);
    }

}
