package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_bill;
import com.hotel.app.service.Status_billService;
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
 * REST controller for managing Status_bill.
 */
@RestController
@RequestMapping("/api")
public class Status_billResource {

    private final Logger log = LoggerFactory.getLogger(Status_billResource.class);
        
    @Inject
    private Status_billService status_billService;
    
    /**
     * POST  /status_bills -> Create a new status_bill.
     */
    @RequestMapping(value = "/status_bills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_bill> createStatus_bill(@Valid @RequestBody Status_bill status_bill) throws URISyntaxException {
        log.debug("REST request to save Status_bill : {}", status_bill);
        if (status_bill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_bill", "idexists", "A new status_bill cannot already have an ID")).body(null);
        }
        Status_bill result = status_billService.save(status_bill);
        return ResponseEntity.created(new URI("/api/status_bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_bill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_bills -> Updates an existing status_bill.
     */
    @RequestMapping(value = "/status_bills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_bill> updateStatus_bill(@Valid @RequestBody Status_bill status_bill) throws URISyntaxException {
        log.debug("REST request to update Status_bill : {}", status_bill);
        if (status_bill.getId() == null) {
            return createStatus_bill(status_bill);
        }
        Status_bill result = status_billService.save(status_bill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_bill", status_bill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_bills -> get all the status_bills.
     */
    @RequestMapping(value = "/status_bills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_bill>> getAllStatus_bills(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_bills");
        Page<Status_bill> page = status_billService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_bills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_bills/:id -> get the "id" status_bill.
     */
    @RequestMapping(value = "/status_bills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_bill> getStatus_bill(@PathVariable Long id) {
        log.debug("REST request to get Status_bill : {}", id);
        Status_bill status_bill = status_billService.findOne(id);
        return Optional.ofNullable(status_bill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_bills/:id -> delete the "id" status_bill.
     */
    @RequestMapping(value = "/status_bills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_bill(@PathVariable Long id) {
        log.debug("REST request to delete Status_bill : {}", id);
        status_billService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_bill", id.toString())).build();
    }
}
