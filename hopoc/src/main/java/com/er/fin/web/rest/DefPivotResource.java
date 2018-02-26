package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DefPivot;
import com.er.fin.service.DefPivotService;
import com.er.fin.service.dto.PivotDataDTO;
import com.er.fin.web.rest.errors.BadRequestAlertException;
import com.er.fin.web.rest.util.HeaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * REST controller for managing DefPivot.
 */
@RestController
@RequestMapping("/api")
public class DefPivotResource {

    private final Logger log = LoggerFactory.getLogger(DefPivotResource.class);

    private static final String ENTITY_NAME = "defPivot";

    private final DefPivotService defPivotService;

    public DefPivotResource(DefPivotService defPivotService) {
        this.defPivotService = defPivotService;
    }

    @GetMapping("/def-pivot-data/{id}")
    @Timed
    public String getDefPivotData(@PathVariable Long id) {
        System.out.println("Pivot Id   = "+id);
        DefPivot defPivotDTO = defPivotService.findOne(id);
        PivotDataDTO pivotData = defPivotService.getSqlData(defPivotDTO.getPvtSql());
        String jsonText = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonText = mapper.writeValueAsString(pivotData);
            System.out.println("Pivot JSON = " + jsonText);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonText;
    }

    /**
     * POST  /def-pivots : Create a new defPivot.
     *
     * @param defPivot the defPivot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new defPivot, or with status 400 (Bad Request) if the defPivot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/def-pivots")
    @Timed
    public ResponseEntity<DefPivot> createDefPivot(@RequestBody DefPivot defPivot) throws URISyntaxException {
        log.debug("REST request to save DefPivot : {}", defPivot);
        if (defPivot.getId() != null) {
            throw new BadRequestAlertException("A new defPivot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefPivot result = defPivotService.save(defPivot);
        return ResponseEntity.created(new URI("/api/def-pivots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /def-pivots : Updates an existing defPivot.
     *
     * @param defPivot the defPivot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated defPivot,
     * or with status 400 (Bad Request) if the defPivot is not valid,
     * or with status 500 (Internal Server Error) if the defPivot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/def-pivots")
    @Timed
    public ResponseEntity<DefPivot> updateDefPivot(@RequestBody DefPivot defPivot) throws URISyntaxException {
        log.debug("REST request to update DefPivot : {}", defPivot);
        if (defPivot.getId() == null) {
            return createDefPivot(defPivot);
        }
        DefPivot result = defPivotService.save(defPivot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, defPivot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /def-pivots : get all the defPivots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of defPivots in body
     */
    @GetMapping("/def-pivots")
    @Timed
    public List<DefPivot> getAllDefPivots() {
        log.debug("REST request to get all DefPivots");
        return defPivotService.findAll();
        }

    /**
     * GET  /def-pivots/:id : get the "id" defPivot.
     *
     * @param id the id of the defPivot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the defPivot, or with status 404 (Not Found)
     */
    @GetMapping("/def-pivots/{id}")
    @Timed
    public ResponseEntity<DefPivot> getDefPivot(@PathVariable Long id) {
        log.debug("REST request to get DefPivot : {}", id);
        DefPivot defPivot = defPivotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(defPivot));
    }

    /**
     * DELETE  /def-pivots/:id : delete the "id" defPivot.
     *
     * @param id the id of the defPivot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/def-pivots/{id}")
    @Timed
    public ResponseEntity<Void> deleteDefPivot(@PathVariable Long id) {
        log.debug("REST request to delete DefPivot : {}", id);
        defPivotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/def-pivots?query=:query : search for the defPivot corresponding
     * to the query.
     *
     * @param query the query of the defPivot search
     * @return the result of the search
     */
    @GetMapping("/_search/def-pivots")
    @Timed
    public List<DefPivot> searchDefPivots(@RequestParam String query) {
        log.debug("REST request to search DefPivots for query {}", query);
        return defPivotService.search(query);
    }

}
