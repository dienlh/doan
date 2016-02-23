package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Room;
import com.hotel.app.service.RoomService;
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
 * REST controller for managing Room.
 */
@RestController
@RequestMapping("/api")
public class RoomResource {

    private final Logger log = LoggerFactory.getLogger(RoomResource.class);
        
    @Inject
    private RoomService roomService;
    
    /**
     * POST  /rooms -> Create a new room.
     */
    @RequestMapping(value = "/rooms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) throws URISyntaxException {
        log.debug("REST request to save Room : {}", room);
        if (room.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("room", "idexists", "A new room cannot already have an ID")).body(null);
        }
        Room result = roomService.save(room);
        return ResponseEntity.created(new URI("/api/rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("room", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rooms -> Updates an existing room.
     */
    @RequestMapping(value = "/rooms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Room> updateRoom(@Valid @RequestBody Room room) throws URISyntaxException {
        log.debug("REST request to update Room : {}", room);
        if (room.getId() == null) {
            return createRoom(room);
        }
        Room result = roomService.save(room);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("room", room.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rooms -> get all the rooms.
     */
    @RequestMapping(value = "/rooms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Room>> getAllRooms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Rooms");
        Page<Room> page = roomService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rooms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rooms/:id -> get the "id" room.
     */
    @RequestMapping(value = "/rooms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        log.debug("REST request to get Room : {}", id);
        Room room = roomService.findOne(id);
        return Optional.ofNullable(room)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rooms/:id -> delete the "id" room.
     */
    @RequestMapping(value = "/rooms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.debug("REST request to delete Room : {}", id);
        roomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("room", id.toString())).build();
    }
}