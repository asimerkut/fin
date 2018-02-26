package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DefItem;
import com.er.fin.service.DefItemService;
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
 * REST controller for managing DefItem.
 */
@RestController
@RequestMapping("/api")
public class DefItemResource {

    private final Logger log = LoggerFactory.getLogger(DefItemResource.class);

    private static final String ENTITY_NAME = "defItem";

    private final DefItemService defItemService;

    public DefItemResource(DefItemService defItemService) {
        this.defItemService = defItemService;
    }

    /**
     * POST  /def-items : Create a new defItem.
     *
     * @param defItem the defItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new defItem, or with status 400 (Bad Request) if the defItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/def-items")
    @Timed
    public ResponseEntity<DefItem> createDefItem(@Valid @RequestBody DefItem defItem) throws URISyntaxException {
        log.debug("REST request to save DefItem : {}", defItem);
        if (defItem.getId() != null) {
            throw new BadRequestAlertException("A new defItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefItem result = defItemService.save(defItem);
        return ResponseEntity.created(new URI("/api/def-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /def-items : Updates an existing defItem.
     *
     * @param defItem the defItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated defItem,
     * or with status 400 (Bad Request) if the defItem is not valid,
     * or with status 500 (Internal Server Error) if the defItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/def-items")
    @Timed
    public ResponseEntity<DefItem> updateDefItem(@Valid @RequestBody DefItem defItem) throws URISyntaxException {
        log.debug("REST request to update DefItem : {}", defItem);
        if (defItem.getId() == null) {
            return createDefItem(defItem);
        }
        DefItem result = defItemService.save(defItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, defItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /def-items : get all the defItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of defItems in body
     */
    @GetMapping("/def-items")
    @Timed
    public List<DefItem> getAllDefItems() {
        log.debug("REST request to get all DefItems");
        return defItemService.findAll();
        }

    /**
     * GET  /def-items/:id : get the "id" defItem.
     *
     * @param id the id of the defItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the defItem, or with status 404 (Not Found)
     */
    @GetMapping("/def-items/{id}")
    @Timed
    public ResponseEntity<DefItem> getDefItem(@PathVariable Long id) {
        log.debug("REST request to get DefItem : {}", id);
        DefItem defItem = defItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(defItem));
    }

    /**
     * DELETE  /def-items/:id : delete the "id" defItem.
     *
     * @param id the id of the defItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/def-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteDefItem(@PathVariable Long id) {
        log.debug("REST request to delete DefItem : {}", id);
        defItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/def-items?query=:query : search for the defItem corresponding
     * to the query.
     *
     * @param query the query of the defItem search
     * @return the result of the search
     */
    @GetMapping("/_search/def-items")
    @Timed
    public List<DefItem> searchDefItems(@RequestParam String query) {
        log.debug("REST request to search DefItems for query {}", query);
        return defItemService.search(query);
    }

}
