package com.er.fin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.er.fin.domain.DefAnswer;
import com.er.fin.service.DefAnswerService;
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
 * REST controller for managing DefAnswer.
 */
@RestController
@RequestMapping("/api")
public class DefAnswerResource {

    private final Logger log = LoggerFactory.getLogger(DefAnswerResource.class);

    private static final String ENTITY_NAME = "defAnswer";

    private final DefAnswerService defAnswerService;

    public DefAnswerResource(DefAnswerService defAnswerService) {
        this.defAnswerService = defAnswerService;
    }

    /**
     * POST  /def-answers : Create a new defAnswer.
     *
     * @param defAnswer the defAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new defAnswer, or with status 400 (Bad Request) if the defAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/def-answers")
    @Timed
    public ResponseEntity<DefAnswer> createDefAnswer(@Valid @RequestBody DefAnswer defAnswer) throws URISyntaxException {
        log.debug("REST request to save DefAnswer : {}", defAnswer);
        if (defAnswer.getId() != null) {
            throw new BadRequestAlertException("A new defAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefAnswer result = defAnswerService.save(defAnswer);
        return ResponseEntity.created(new URI("/api/def-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /def-answers : Updates an existing defAnswer.
     *
     * @param defAnswer the defAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated defAnswer,
     * or with status 400 (Bad Request) if the defAnswer is not valid,
     * or with status 500 (Internal Server Error) if the defAnswer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/def-answers")
    @Timed
    public ResponseEntity<DefAnswer> updateDefAnswer(@Valid @RequestBody DefAnswer defAnswer) throws URISyntaxException {
        log.debug("REST request to update DefAnswer : {}", defAnswer);
        if (defAnswer.getId() == null) {
            return createDefAnswer(defAnswer);
        }
        DefAnswer result = defAnswerService.save(defAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, defAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /def-answers : get all the defAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of defAnswers in body
     */
    @GetMapping("/def-answers")
    @Timed
    public List<DefAnswer> getAllDefAnswers() {
        log.debug("REST request to get all DefAnswers");
        return defAnswerService.findAll();
        }

    /**
     * GET  /def-answers/:id : get the "id" defAnswer.
     *
     * @param id the id of the defAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the defAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/def-answers/{id}")
    @Timed
    public ResponseEntity<DefAnswer> getDefAnswer(@PathVariable Long id) {
        log.debug("REST request to get DefAnswer : {}", id);
        DefAnswer defAnswer = defAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(defAnswer));
    }

    /**
     * DELETE  /def-answers/:id : delete the "id" defAnswer.
     *
     * @param id the id of the defAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/def-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteDefAnswer(@PathVariable Long id) {
        log.debug("REST request to delete DefAnswer : {}", id);
        defAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/def-answers?query=:query : search for the defAnswer corresponding
     * to the query.
     *
     * @param query the query of the defAnswer search
     * @return the result of the search
     */
    @GetMapping("/_search/def-answers")
    @Timed
    public List<DefAnswer> searchDefAnswers(@RequestParam String query) {
        log.debug("REST request to search DefAnswers for query {}", query);
        return defAnswerService.search(query);
    }

}
