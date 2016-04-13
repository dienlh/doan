package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Marital_status;
import com.hotel.app.service.Marital_statusService;
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
 * REST controller for managing Marital_status.
 */
@RestController
@RequestMapping("/api")
public class Marital_statusResource {

    private final Logger log = LoggerFactory.getLogger(Marital_statusResource.class);
        
    @Inject
    private Marital_statusService marital_statusService;
    
    /**
     * POST  /marital_statuss -> Create a new marital_status.
     */
    @RequestMapping(value = "/marital_statuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Marital_status> createMarital_status(@Valid @RequestBody Marital_status marital_status) throws URISyntaxException {
        log.debug("REST request to save Marital_status : {}", marital_status);
        if (marital_status.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("marital_status", "idexists", "A new marital_status cannot already have an ID")).body(null);
        }
        Marital_status result = marital_statusService.save(marital_status);
        return ResponseEntity.created(new URI("/api/marital_statuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("marital_status", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marital_statuss -> Updates an existing marital_status.
     */
    @RequestMapping(value = "/marital_statuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Marital_status> updateMarital_status(@Valid @RequestBody Marital_status marital_status) throws URISyntaxException {
        log.debug("REST request to update Marital_status : {}", marital_status);
        if (marital_status.getId() == null) {
            return createMarital_status(marital_status);
        }
        Marital_status result = marital_statusService.save(marital_status);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("marital_status", marital_status.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marital_statuss -> get all the marital_statuss.
     */
    @RequestMapping(value = "/marital_statuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Marital_status>> getAllMarital_statuss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Marital_statuss");
        Page<Marital_status> page = marital_statusService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/marital_statuss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /marital_statuss/:id -> get the "id" marital_status.
     */
    @RequestMapping(value = "/marital_statuss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Marital_status> getMarital_status(@PathVariable Long id) {
        log.debug("REST request to get Marital_status : {}", id);
        Marital_status marital_status = marital_statusService.findOne(id);
        return Optional.ofNullable(marital_status)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /marital_statuss/:id -> delete the "id" marital_status.
     */
    @RequestMapping(value = "/marital_statuss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMarital_status(@PathVariable Long id) {
        log.debug("REST request to delete Marital_status : {}", id);
        marital_statusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("marital_status", id.toString())).build();
    }
}
