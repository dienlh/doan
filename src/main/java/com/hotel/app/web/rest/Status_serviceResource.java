package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_service;
import com.hotel.app.service.Status_serviceService;
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
 * REST controller for managing Status_service.
 */
@RestController
@RequestMapping("/api")
public class Status_serviceResource {

    private final Logger log = LoggerFactory.getLogger(Status_serviceResource.class);
        
    @Inject
    private Status_serviceService status_serviceService;
    
    /**
     * POST  /status_services -> Create a new status_service.
     */
    @RequestMapping(value = "/status_services",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_service> createStatus_service(@Valid @RequestBody Status_service status_service) throws URISyntaxException {
        log.debug("REST request to save Status_service : {}", status_service);
        if (status_service.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_service", "idexists", "A new status_service cannot already have an ID")).body(null);
        }
        Status_service result = status_serviceService.save(status_service);
        return ResponseEntity.created(new URI("/api/status_services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_service", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_services -> Updates an existing status_service.
     */
    @RequestMapping(value = "/status_services",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_service> updateStatus_service(@Valid @RequestBody Status_service status_service) throws URISyntaxException {
        log.debug("REST request to update Status_service : {}", status_service);
        if (status_service.getId() == null) {
            return createStatus_service(status_service);
        }
        Status_service result = status_serviceService.save(status_service);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_service", status_service.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_services -> get all the status_services.
     */
    @RequestMapping(value = "/status_services",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_service>> getAllStatus_services(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_services");
        Page<Status_service> page = status_serviceService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_services/:id -> get the "id" status_service.
     */
    @RequestMapping(value = "/status_services/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_service> getStatus_service(@PathVariable Long id) {
        log.debug("REST request to get Status_service : {}", id);
        Status_service status_service = status_serviceService.findOne(id);
        return Optional.ofNullable(status_service)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_services/:id -> delete the "id" status_service.
     */
    @RequestMapping(value = "/status_services/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_service(@PathVariable Long id) {
        log.debug("REST request to delete Status_service : {}", id);
        status_serviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_service", id.toString())).build();
    }
}
