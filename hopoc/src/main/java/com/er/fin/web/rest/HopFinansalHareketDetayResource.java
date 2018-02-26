package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.HopFinansalHareketDetay;
import com.er.fin.service.HopFinansalHareketDetayService;
import com.er.fin.web.rest.errors.BadRequestAlertException;
import com.er.fin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HopFinansalHareketDetay.
 */
@RestController
@RequestMapping("/api")
public class HopFinansalHareketDetayResource {

    private final Logger log = LoggerFactory.getLogger(HopFinansalHareketDetayResource.class);

    private static final String ENTITY_NAME = "hopFinansalHareketDetay";

    private final HopFinansalHareketDetayService hopFinansalHareketDetayService;

    public HopFinansalHareketDetayResource(HopFinansalHareketDetayService hopFinansalHareketDetayService) {
        this.hopFinansalHareketDetayService = hopFinansalHareketDetayService;
    }

    /**
     * POST  /hop-finansal-hareket-detays : Create a new hopFinansalHareketDetay.
     *
     * @param hopFinansalHareketDetay the hopFinansalHareketDetay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hopFinansalHareketDetay, or with status 400 (Bad Request) if the hopFinansalHareketDetay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hop-finansal-hareket-detays")
    @Timed
    public ResponseEntity<HopFinansalHareketDetay> createHopFinansalHareketDetay(@RequestBody HopFinansalHareketDetay hopFinansalHareketDetay) throws URISyntaxException {
        log.debug("REST request to save HopFinansalHareketDetay : {}", hopFinansalHareketDetay);
        if (hopFinansalHareketDetay.getId() != null) {
            throw new BadRequestAlertException("A new hopFinansalHareketDetay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HopFinansalHareketDetay result = hopFinansalHareketDetayService.save(hopFinansalHareketDetay);
        return ResponseEntity.created(new URI("/api/hop-finansal-hareket-detays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hop-finansal-hareket-detays : Updates an existing hopFinansalHareketDetay.
     *
     * @param hopFinansalHareketDetay the hopFinansalHareketDetay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hopFinansalHareketDetay,
     * or with status 400 (Bad Request) if the hopFinansalHareketDetay is not valid,
     * or with status 500 (Internal Server Error) if the hopFinansalHareketDetay couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hop-finansal-hareket-detays")
    @Timed
    public ResponseEntity<HopFinansalHareketDetay> updateHopFinansalHareketDetay(@RequestBody HopFinansalHareketDetay hopFinansalHareketDetay) throws URISyntaxException {
        log.debug("REST request to update HopFinansalHareketDetay : {}", hopFinansalHareketDetay);
        if (hopFinansalHareketDetay.getId() == null) {
            return createHopFinansalHareketDetay(hopFinansalHareketDetay);
        }
        HopFinansalHareketDetay result = hopFinansalHareketDetayService.save(hopFinansalHareketDetay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hopFinansalHareketDetay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hop-finansal-hareket-detays : get all the hopFinansalHareketDetays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hopFinansalHareketDetays in body
     */
    @GetMapping("/hop-finansal-hareket-detays")
    @Timed
    public List<HopFinansalHareketDetay> getAllHopFinansalHareketDetays() {
        log.debug("REST request to get all HopFinansalHareketDetays");
        return hopFinansalHareketDetayService.findAll();
        }

    /**
     * GET  /hop-finansal-hareket-detays/:id : get the "id" hopFinansalHareketDetay.
     *
     * @param id the id of the hopFinansalHareketDetay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hopFinansalHareketDetay, or with status 404 (Not Found)
     */
    @GetMapping("/hop-finansal-hareket-detays/{id}")
    @Timed
    public ResponseEntity<HopFinansalHareketDetay> getHopFinansalHareketDetay(@PathVariable Long id) {
        log.debug("REST request to get HopFinansalHareketDetay : {}", id);
        HopFinansalHareketDetay hopFinansalHareketDetay = hopFinansalHareketDetayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hopFinansalHareketDetay));
    }

    /**
     * DELETE  /hop-finansal-hareket-detays/:id : delete the "id" hopFinansalHareketDetay.
     *
     * @param id the id of the hopFinansalHareketDetay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hop-finansal-hareket-detays/{id}")
    @Timed
    public ResponseEntity<Void> deleteHopFinansalHareketDetay(@PathVariable Long id) {
        log.debug("REST request to delete HopFinansalHareketDetay : {}", id);
        hopFinansalHareketDetayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hop-finansal-hareket-detays?query=:query : search for the hopFinansalHareketDetay corresponding
     * to the query.
     *
     * @param query the query of the hopFinansalHareketDetay search
     * @return the result of the search
     */
    @GetMapping("/_search/hop-finansal-hareket-detays")
    @Timed
    public List<HopFinansalHareketDetay> searchHopFinansalHareketDetays(@RequestParam String query) {
        log.debug("REST request to search HopFinansalHareketDetays for query {}", query);
        return hopFinansalHareketDetayService.search(query);
    }

}
