package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_bill_service;
import com.hotel.app.service.Status_bill_serviceService;
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
 * REST controller for managing Status_bill_service.
 */
@RestController
@RequestMapping("/api")
public class Status_bill_serviceResource {

    private final Logger log = LoggerFactory.getLogger(Status_bill_serviceResource.class);
        
    @Inject
    private Status_bill_serviceService status_bill_serviceService;
    
    /**
     * POST  /status_bill_services -> Create a new status_bill_service.
     */
    @RequestMapping(value = "/status_bill_services",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_bill_service> createStatus_bill_service(@Valid @RequestBody Status_bill_service status_bill_service) throws URISyntaxException {
        log.debug("REST request to save Status_bill_service : {}", status_bill_service);
        if (status_bill_service.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_bill_service", "idexists", "A new status_bill_service cannot already have an ID")).body(null);
        }
        Status_bill_service result = status_bill_serviceService.save(status_bill_service);
        return ResponseEntity.created(new URI("/api/status_bill_services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_bill_service", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_bill_services -> Updates an existing status_bill_service.
     */
    @RequestMapping(value = "/status_bill_services",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_bill_service> updateStatus_bill_service(@Valid @RequestBody Status_bill_service status_bill_service) throws URISyntaxException {
        log.debug("REST request to update Status_bill_service : {}", status_bill_service);
        if (status_bill_service.getId() == null) {
            return createStatus_bill_service(status_bill_service);
        }
        Status_bill_service result = status_bill_serviceService.save(status_bill_service);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_bill_service", status_bill_service.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_bill_services -> get all the status_bill_services.
     */
    @RequestMapping(value = "/status_bill_services",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_bill_service>> getAllStatus_bill_services(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_bill_services");
        Page<Status_bill_service> page = status_bill_serviceService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_bill_services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_bill_services/:id -> get the "id" status_bill_service.
     */
    @RequestMapping(value = "/status_bill_services/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_bill_service> getStatus_bill_service(@PathVariable Long id) {
        log.debug("REST request to get Status_bill_service : {}", id);
        Status_bill_service status_bill_service = status_bill_serviceService.findOne(id);
        return Optional.ofNullable(status_bill_service)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_bill_services/:id -> delete the "id" status_bill_service.
     */
    @RequestMapping(value = "/status_bill_services/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_bill_service(@PathVariable Long id) {
        log.debug("REST request to delete Status_bill_service : {}", id);
        status_bill_serviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_bill_service", id.toString())).build();
    }
}
