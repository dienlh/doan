package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Religion;
import com.hotel.app.service.ReligionService;
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
 * REST controller for managing Religion.
 */
@RestController
@RequestMapping("/api")
public class ReligionResource {

    private final Logger log = LoggerFactory.getLogger(ReligionResource.class);
        
    @Inject
    private ReligionService religionService;
    
    /**
     * POST  /religions -> Create a new religion.
     */
    @RequestMapping(value = "/religions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Religion> createReligion(@Valid @RequestBody Religion religion) throws URISyntaxException {
        log.debug("REST request to save Religion : {}", religion);
        if (religion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("religion", "idexists", "A new religion cannot already have an ID")).body(null);
        }
        Religion result = religionService.save(religion);
        return ResponseEntity.created(new URI("/api/religions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("religion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /religions -> Updates an existing religion.
     */
    @RequestMapping(value = "/religions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Religion> updateReligion(@Valid @RequestBody Religion religion) throws URISyntaxException {
        log.debug("REST request to update Religion : {}", religion);
        if (religion.getId() == null) {
            return createReligion(religion);
        }
        Religion result = religionService.save(religion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("religion", religion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /religions -> get all the religions.
     */
    @RequestMapping(value = "/religions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Religion>> getAllReligions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Religions");
        Page<Religion> page = religionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/religions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /religions/:id -> get the "id" religion.
     */
    @RequestMapping(value = "/religions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Religion> getReligion(@PathVariable Long id) {
        log.debug("REST request to get Religion : {}", id);
        Religion religion = religionService.findOne(id);
        return Optional.ofNullable(religion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /religions/:id -> delete the "id" religion.
     */
    @RequestMapping(value = "/religions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReligion(@PathVariable Long id) {
        log.debug("REST request to delete Religion : {}", id);
        religionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("religion", id.toString())).build();
    }
}
