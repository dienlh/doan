package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_event;
import com.hotel.app.service.Status_eventService;
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
 * REST controller for managing Status_event.
 */
@RestController
@RequestMapping("/api")
public class Status_eventResource {

    private final Logger log = LoggerFactory.getLogger(Status_eventResource.class);
        
    @Inject
    private Status_eventService status_eventService;
    
    /**
     * POST  /status_events -> Create a new status_event.
     */
    @RequestMapping(value = "/status_events",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_event> createStatus_event(@Valid @RequestBody Status_event status_event) throws URISyntaxException {
        log.debug("REST request to save Status_event : {}", status_event);
        if (status_event.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_event", "idexists", "A new status_event cannot already have an ID")).body(null);
        }
        Status_event result = status_eventService.save(status_event);
        return ResponseEntity.created(new URI("/api/status_events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_event", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_events -> Updates an existing status_event.
     */
    @RequestMapping(value = "/status_events",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_event> updateStatus_event(@Valid @RequestBody Status_event status_event) throws URISyntaxException {
        log.debug("REST request to update Status_event : {}", status_event);
        if (status_event.getId() == null) {
            return createStatus_event(status_event);
        }
        Status_event result = status_eventService.save(status_event);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_event", status_event.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_events -> get all the status_events.
     */
    @RequestMapping(value = "/status_events",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_event>> getAllStatus_events(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_events");
        Page<Status_event> page = status_eventService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_events/:id -> get the "id" status_event.
     */
    @RequestMapping(value = "/status_events/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_event> getStatus_event(@PathVariable Long id) {
        log.debug("REST request to get Status_event : {}", id);
        Status_event status_event = status_eventService.findOne(id);
        return Optional.ofNullable(status_event)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_events/:id -> delete the "id" status_event.
     */
    @RequestMapping(value = "/status_events/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_event(@PathVariable Long id) {
        log.debug("REST request to delete Status_event : {}", id);
        status_eventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_event", id.toString())).build();
    }
}
