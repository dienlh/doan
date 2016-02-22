package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_register;
import com.hotel.app.service.Status_registerService;
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
 * REST controller for managing Status_register.
 */
@RestController
@RequestMapping("/api")
public class Status_registerResource {

    private final Logger log = LoggerFactory.getLogger(Status_registerResource.class);
        
    @Inject
    private Status_registerService status_registerService;
    
    /**
     * POST  /status_registers -> Create a new status_register.
     */
    @RequestMapping(value = "/status_registers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_register> createStatus_register(@Valid @RequestBody Status_register status_register) throws URISyntaxException {
        log.debug("REST request to save Status_register : {}", status_register);
        if (status_register.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_register", "idexists", "A new status_register cannot already have an ID")).body(null);
        }
        Status_register result = status_registerService.save(status_register);
        return ResponseEntity.created(new URI("/api/status_registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_register", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_registers -> Updates an existing status_register.
     */
    @RequestMapping(value = "/status_registers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_register> updateStatus_register(@Valid @RequestBody Status_register status_register) throws URISyntaxException {
        log.debug("REST request to update Status_register : {}", status_register);
        if (status_register.getId() == null) {
            return createStatus_register(status_register);
        }
        Status_register result = status_registerService.save(status_register);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_register", status_register.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_registers -> get all the status_registers.
     */
    @RequestMapping(value = "/status_registers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_register>> getAllStatus_registers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_registers");
        Page<Status_register> page = status_registerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_registers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_registers/:id -> get the "id" status_register.
     */
    @RequestMapping(value = "/status_registers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_register> getStatus_register(@PathVariable Long id) {
        log.debug("REST request to get Status_register : {}", id);
        Status_register status_register = status_registerService.findOne(id);
        return Optional.ofNullable(status_register)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_registers/:id -> delete the "id" status_register.
     */
    @RequestMapping(value = "/status_registers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_register(@PathVariable Long id) {
        log.debug("REST request to delete Status_register : {}", id);
        status_registerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_register", id.toString())).build();
    }
}
