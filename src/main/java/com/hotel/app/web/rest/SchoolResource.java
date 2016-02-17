package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.School;
import com.hotel.app.service.SchoolService;
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
 * REST controller for managing School.
 */
@RestController
@RequestMapping("/api")
public class SchoolResource {

    private final Logger log = LoggerFactory.getLogger(SchoolResource.class);
        
    @Inject
    private SchoolService schoolService;
    
    /**
     * POST  /schools -> Create a new school.
     */
    @RequestMapping(value = "/schools",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<School> createSchool(@Valid @RequestBody School school) throws URISyntaxException {
        log.debug("REST request to save School : {}", school);
        if (school.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("school", "idexists", "A new school cannot already have an ID")).body(null);
        }
        School result = schoolService.save(school);
        return ResponseEntity.created(new URI("/api/schools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("school", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schools -> Updates an existing school.
     */
    @RequestMapping(value = "/schools",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<School> updateSchool(@Valid @RequestBody School school) throws URISyntaxException {
        log.debug("REST request to update School : {}", school);
        if (school.getId() == null) {
            return createSchool(school);
        }
        School result = schoolService.save(school);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("school", school.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schools -> get all the schools.
     */
    @RequestMapping(value = "/schools",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<School>> getAllSchools(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Schools");
        Page<School> page = schoolService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schools");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schools/:id -> get the "id" school.
     */
    @RequestMapping(value = "/schools/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<School> getSchool(@PathVariable Long id) {
        log.debug("REST request to get School : {}", id);
        School school = schoolService.findOne(id);
        return Optional.ofNullable(school)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /schools/:id -> delete the "id" school.
     */
    @RequestMapping(value = "/schools/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        log.debug("REST request to delete School : {}", id);
        schoolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("school", id.toString())).build();
    }
}
