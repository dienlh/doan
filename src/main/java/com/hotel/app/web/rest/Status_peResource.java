package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_pe;
import com.hotel.app.service.Status_peService;
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
 * REST controller for managing Status_pe.
 */
@RestController
@RequestMapping("/api")
public class Status_peResource {

    private final Logger log = LoggerFactory.getLogger(Status_peResource.class);
        
    @Inject
    private Status_peService status_peService;
    
    /**
     * POST  /status_pes -> Create a new status_pe.
     */
    @RequestMapping(value = "/status_pes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_pe> createStatus_pe(@Valid @RequestBody Status_pe status_pe) throws URISyntaxException {
        log.debug("REST request to save Status_pe : {}", status_pe);
        if (status_pe.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_pe", "idexists", "A new status_pe cannot already have an ID")).body(null);
        }
        Status_pe result = status_peService.save(status_pe);
        return ResponseEntity.created(new URI("/api/status_pes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_pe", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_pes -> Updates an existing status_pe.
     */
    @RequestMapping(value = "/status_pes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_pe> updateStatus_pe(@Valid @RequestBody Status_pe status_pe) throws URISyntaxException {
        log.debug("REST request to update Status_pe : {}", status_pe);
        if (status_pe.getId() == null) {
            return createStatus_pe(status_pe);
        }
        Status_pe result = status_peService.save(status_pe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_pe", status_pe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_pes -> get all the status_pes.
     */
    @RequestMapping(value = "/status_pes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_pe>> getAllStatus_pes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_pes");
        Page<Status_pe> page = status_peService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_pes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_pes/:id -> get the "id" status_pe.
     */
    @RequestMapping(value = "/status_pes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_pe> getStatus_pe(@PathVariable Long id) {
        log.debug("REST request to get Status_pe : {}", id);
        Status_pe status_pe = status_peService.findOne(id);
        return Optional.ofNullable(status_pe)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_pes/:id -> delete the "id" status_pe.
     */
    @RequestMapping(value = "/status_pes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_pe(@PathVariable Long id) {
        log.debug("REST request to delete Status_pe : {}", id);
        status_peService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_pe", id.toString())).build();
    }
}
