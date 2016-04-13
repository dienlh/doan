package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Type_room;
import com.hotel.app.service.Type_roomService;
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
 * REST controller for managing Type_room.
 */
@RestController
@RequestMapping("/api")
public class Type_roomResource {

    private final Logger log = LoggerFactory.getLogger(Type_roomResource.class);
        
    @Inject
    private Type_roomService type_roomService;
    
    /**
     * POST  /type_rooms -> Create a new type_room.
     */
    @RequestMapping(value = "/type_rooms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_room> createType_room(@Valid @RequestBody Type_room type_room) throws URISyntaxException {
        log.debug("REST request to save Type_room : {}", type_room);
        if (type_room.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("type_room", "idexists", "A new type_room cannot already have an ID")).body(null);
        }
        Type_room result = type_roomService.save(type_room);
        return ResponseEntity.created(new URI("/api/type_rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("type_room", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type_rooms -> Updates an existing type_room.
     */
    @RequestMapping(value = "/type_rooms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_room> updateType_room(@Valid @RequestBody Type_room type_room) throws URISyntaxException {
        log.debug("REST request to update Type_room : {}", type_room);
        if (type_room.getId() == null) {
            return createType_room(type_room);
        }
        Type_room result = type_roomService.save(type_room);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("type_room", type_room.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type_rooms -> get all the type_rooms.
     */
    @RequestMapping(value = "/type_rooms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Type_room>> getAllType_rooms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Type_rooms");
        Page<Type_room> page = type_roomService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type_rooms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type_rooms/:id -> get the "id" type_room.
     */
    @RequestMapping(value = "/type_rooms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_room> getType_room(@PathVariable Long id) {
        log.debug("REST request to get Type_room : {}", id);
        Type_room type_room = type_roomService.findOne(id);
        return Optional.ofNullable(type_room)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type_rooms/:id -> delete the "id" type_room.
     */
    @RequestMapping(value = "/type_rooms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteType_room(@PathVariable Long id) {
        log.debug("REST request to delete Type_room : {}", id);
        type_roomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("type_room", id.toString())).build();
    }
}
