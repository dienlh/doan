package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Major;
import com.hotel.app.service.MajorService;
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
 * REST controller for managing Major.
 */
@RestController
@RequestMapping("/api")
public class MajorResource {

    private final Logger log = LoggerFactory.getLogger(MajorResource.class);
        
    @Inject
    private MajorService majorService;
    
    /**
     * POST  /majors -> Create a new major.
     */
    @RequestMapping(value = "/majors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Major> createMajor(@Valid @RequestBody Major major) throws URISyntaxException {
        log.debug("REST request to save Major : {}", major);
        if (major.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("major", "idexists", "A new major cannot already have an ID")).body(null);
        }
        Major result = majorService.save(major);
        return ResponseEntity.created(new URI("/api/majors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("major", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /majors -> Updates an existing major.
     */
    @RequestMapping(value = "/majors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Major> updateMajor(@Valid @RequestBody Major major) throws URISyntaxException {
        log.debug("REST request to update Major : {}", major);
        if (major.getId() == null) {
            return createMajor(major);
        }
        Major result = majorService.save(major);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("major", major.getId().toString()))
            .body(result);
    }

    /**
     * GET  /majors -> get all the majors.
     */
    @RequestMapping(value = "/majors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Major>> getAllMajors(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Majors");
        Page<Major> page = majorService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/majors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /majors/:id -> get the "id" major.
     */
    @RequestMapping(value = "/majors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Major> getMajor(@PathVariable Long id) {
        log.debug("REST request to get Major : {}", id);
        Major major = majorService.findOne(id);
        return Optional.ofNullable(major)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /majors/:id -> delete the "id" major.
     */
    @RequestMapping(value = "/majors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMajor(@PathVariable Long id) {
        log.debug("REST request to delete Major : {}", id);
        majorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("major", id.toString())).build();
    }
}
