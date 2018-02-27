package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.PerPlan;
import com.er.fin.service.PerPlanService;
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
 * REST controller for managing PerPlan.
 */
@RestController
@RequestMapping("/api")
public class PerPlanResource {

    private final Logger log = LoggerFactory.getLogger(PerPlanResource.class);

    private static final String ENTITY_NAME = "perPlan";

    private final PerPlanService perPlanService;

    public PerPlanResource(PerPlanService perPlanService) {
        this.perPlanService = perPlanService;
    }

    /**
     * POST  /per-plans : Create a new perPlan.
     *
     * @param perPlan the perPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perPlan, or with status 400 (Bad Request) if the perPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/per-plans")
    @Timed
    public ResponseEntity<PerPlan> createPerPlan(@Valid @RequestBody PerPlan perPlan) throws URISyntaxException {
        log.debug("REST request to save PerPlan : {}", perPlan);
        if (perPlan.getId() != null) {
            throw new BadRequestAlertException("A new perPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerPlan result = perPlanService.save(perPlan);
        return ResponseEntity.created(new URI("/api/per-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /per-plans : Updates an existing perPlan.
     *
     * @param perPlan the perPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perPlan,
     * or with status 400 (Bad Request) if the perPlan is not valid,
     * or with status 500 (Internal Server Error) if the perPlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/per-plans")
    @Timed
    public ResponseEntity<PerPlan> updatePerPlan(@Valid @RequestBody PerPlan perPlan) throws URISyntaxException {
        log.debug("REST request to update PerPlan : {}", perPlan);
        if (perPlan.getId() == null) {
            return createPerPlan(perPlan);
        }
        PerPlan result = perPlanService.save(perPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perPlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /per-plans : get all the perPlans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of perPlans in body
     */
    @GetMapping("/per-plans")
    @Timed
    public List<PerPlan> getAllPerPlans() {
        log.debug("REST request to get all PerPlans");
        return perPlanService.findAll();
        }

    /**
     * GET  /per-plans/:id : get the "id" perPlan.
     *
     * @param id the id of the perPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perPlan, or with status 404 (Not Found)
     */
    @GetMapping("/per-plans/{id}")
    @Timed
    public ResponseEntity<PerPlan> getPerPlan(@PathVariable Long id) {
        log.debug("REST request to get PerPlan : {}", id);
        PerPlan perPlan = perPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perPlan));
    }

    /**
     * DELETE  /per-plans/:id : delete the "id" perPlan.
     *
     * @param id the id of the perPlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/per-plans/{id}")
    @Timed
    public ResponseEntity<Void> deletePerPlan(@PathVariable Long id) {
        log.debug("REST request to delete PerPlan : {}", id);
        perPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/per-plans?query=:query : search for the perPlan corresponding
     * to the query.
     *
     * @param query the query of the perPlan search
     * @return the result of the search
     */
    @GetMapping("/_search/per-plans")
    @Timed
    public List<PerPlan> searchPerPlans(@RequestParam String query) {
        log.debug("REST request to search PerPlans for query {}", query);
        return perPlanService.search(query);
    }

}
