package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_room;
import com.hotel.app.service.Status_roomService;
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
 * REST controller for managing Status_room.
 */
@RestController
@RequestMapping("/api")
public class Status_roomResource {

    private final Logger log = LoggerFactory.getLogger(Status_roomResource.class);
        
    @Inject
    private Status_roomService status_roomService;
    
    /**
     * POST  /status_rooms -> Create a new status_room.
     */
    @RequestMapping(value = "/status_rooms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_room> createStatus_room(@Valid @RequestBody Status_room status_room) throws URISyntaxException {
        log.debug("REST request to save Status_room : {}", status_room);
        if (status_room.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_room", "idexists", "A new status_room cannot already have an ID")).body(null);
        }
        Status_room result = status_roomService.save(status_room);
        return ResponseEntity.created(new URI("/api/status_rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_room", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_rooms -> Updates an existing status_room.
     */
    @RequestMapping(value = "/status_rooms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_room> updateStatus_room(@Valid @RequestBody Status_room status_room) throws URISyntaxException {
        log.debug("REST request to update Status_room : {}", status_room);
        if (status_room.getId() == null) {
            return createStatus_room(status_room);
        }
        Status_room result = status_roomService.save(status_room);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_room", status_room.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_rooms -> get all the status_rooms.
     */
    @RequestMapping(value = "/status_rooms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_room>> getAllStatus_rooms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_rooms");
        Page<Status_room> page = status_roomService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_rooms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_rooms/:id -> get the "id" status_room.
     */
    @RequestMapping(value = "/status_rooms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_room> getStatus_room(@PathVariable Long id) {
        log.debug("REST request to get Status_room : {}", id);
        Status_room status_room = status_roomService.findOne(id);
        return Optional.ofNullable(status_room)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_rooms/:id -> delete the "id" status_room.
     */
    @RequestMapping(value = "/status_rooms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_room(@PathVariable Long id) {
        log.debug("REST request to delete Status_room : {}", id);
        status_roomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_room", id.toString())).build();
    }
}
