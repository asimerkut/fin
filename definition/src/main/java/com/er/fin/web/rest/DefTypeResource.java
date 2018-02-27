package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DefType;
import com.er.fin.service.DefTypeService;
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
 * REST controller for managing DefType.
 */
@RestController
@RequestMapping("/api")
public class DefTypeResource {

    private final Logger log = LoggerFactory.getLogger(DefTypeResource.class);

    private static final String ENTITY_NAME = "defType";

    private final DefTypeService defTypeService;

    public DefTypeResource(DefTypeService defTypeService) {
        this.defTypeService = defTypeService;
    }

    /**
     * POST  /def-types : Create a new defType.
     *
     * @param defType the defType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new defType, or with status 400 (Bad Request) if the defType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/def-types")
    @Timed
    public ResponseEntity<DefType> createDefType(@Valid @RequestBody DefType defType) throws URISyntaxException {
        log.debug("REST request to save DefType : {}", defType);
        if (defType.getId() != null) {
            throw new BadRequestAlertException("A new defType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefType result = defTypeService.save(defType);
        return ResponseEntity.created(new URI("/api/def-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /def-types : Updates an existing defType.
     *
     * @param defType the defType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated defType,
     * or with status 400 (Bad Request) if the defType is not valid,
     * or with status 500 (Internal Server Error) if the defType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/def-types")
    @Timed
    public ResponseEntity<DefType> updateDefType(@Valid @RequestBody DefType defType) throws URISyntaxException {
        log.debug("REST request to update DefType : {}", defType);
        if (defType.getId() == null) {
            return createDefType(defType);
        }
        DefType result = defTypeService.save(defType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, defType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /def-types : get all the defTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of defTypes in body
     */
    @GetMapping("/def-types")
    @Timed
    public List<DefType> getAllDefTypes() {
        log.debug("REST request to get all DefTypes");
        return defTypeService.findAll();
        }

    /**
     * GET  /def-types/:id : get the "id" defType.
     *
     * @param id the id of the defType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the defType, or with status 404 (Not Found)
     */
    @GetMapping("/def-types/{id}")
    @Timed
    public ResponseEntity<DefType> getDefType(@PathVariable Long id) {
        log.debug("REST request to get DefType : {}", id);
        DefType defType = defTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(defType));
    }

    /**
     * DELETE  /def-types/:id : delete the "id" defType.
     *
     * @param id the id of the defType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/def-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDefType(@PathVariable Long id) {
        log.debug("REST request to delete DefType : {}", id);
        defTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/def-types?query=:query : search for the defType corresponding
     * to the query.
     *
     * @param query the query of the defType search
     * @return the result of the search
     */
    @GetMapping("/_search/def-types")
    @Timed
    public List<DefType> searchDefTypes(@RequestParam String query) {
        log.debug("REST request to search DefTypes for query {}", query);
        return defTypeService.search(query);
    }

}
