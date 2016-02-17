package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Education_level;
import com.hotel.app.service.Education_levelService;
import com.hotel.app.web.rest.util.HeaderUtil;
import com.hotel.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Education_level.
 */
@RestController
@RequestMapping("/api")
public class Education_levelResource {

    private final Logger log = LoggerFactory.getLogger(Education_levelResource.class);
        
    @Inject
    private Education_levelService education_levelService;
    
    /**
     * POST  /education_levels -> Create a new education_level.
     */
    @RequestMapping(value = "/education_levels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Education_level> createEducation_level(@Valid @RequestBody Education_level education_level) throws URISyntaxException {
        log.debug("REST request to save Education_level : {}", education_level);
        if (education_level.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("education_level", "idexists", "A new education_level cannot already have an ID")).body(null);
        }
        Education_level result = education_levelService.save(education_level);
        return ResponseEntity.created(new URI("/api/education_levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("education_level", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /education_levels -> Updates an existing education_level.
     */
    @RequestMapping(value = "/education_levels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Education_level> updateEducation_level(@Valid @RequestBody Education_level education_level) throws URISyntaxException {
        log.debug("REST request to update Education_level : {}", education_level);
        if (education_level.getId() == null) {
            return createEducation_level(education_level);
        }
        Education_level result = education_levelService.save(education_level);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("education_level", education_level.getId().toString()))
            .body(result);
    }

    /**
     * GET  /education_levels -> get all the education_levels.
     */
    @RequestMapping(value = "/education_levels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Education_level>> getAllEducation_levels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Education_levels");
        Page<Education_level> page = education_levelService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/education_levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /education_levels/:id -> get the "id" education_level.
     */
    @RequestMapping(value = "/education_levels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Education_level> getEducation_level(@PathVariable Long id) {
        log.debug("REST request to get Education_level : {}", id);
        Education_level education_level = education_levelService.findOne(id);
        return Optional.ofNullable(education_level)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /education_levels/:id -> delete the "id" education_level.
     */
    @RequestMapping(value = "/education_levels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEducation_level(@PathVariable Long id) {
        log.debug("REST request to delete Education_level : {}", id);
        education_levelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("education_level", id.toString())).build();
    }
}
