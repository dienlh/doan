package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Position;
import com.hotel.app.service.PositionService;
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
 * REST controller for managing Position.
 */
@RestController
@RequestMapping("/api")
public class PositionResource {

    private final Logger log = LoggerFactory.getLogger(PositionResource.class);
        
    @Inject
    private PositionService positionService;
    
    /**
     * POST  /positions -> Create a new position.
     */
    @RequestMapping(value = "/positions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Position> createPosition(@Valid @RequestBody Position position) throws URISyntaxException {
        log.debug("REST request to save Position : {}", position);
        if (position.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("position", "idexists", "A new position cannot already have an ID")).body(null);
        }
        Position result = positionService.save(position);
        return ResponseEntity.created(new URI("/api/positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("position", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /positions -> Updates an existing position.
     */
    @RequestMapping(value = "/positions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Position> updatePosition(@Valid @RequestBody Position position) throws URISyntaxException {
        log.debug("REST request to update Position : {}", position);
        if (position.getId() == null) {
            return createPosition(position);
        }
        Position result = positionService.save(position);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("position", position.getId().toString()))
            .body(result);
    }

    /**
     * GET  /positions -> get all the positions.
     */
    @RequestMapping(value = "/positions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Position>> getAllPositions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Positions");
        Page<Position> page = positionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/positions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /positions/:id -> get the "id" position.
     */
    @RequestMapping(value = "/positions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Position> getPosition(@PathVariable Long id) {
        log.debug("REST request to get Position : {}", id);
        Position position = positionService.findOne(id);
        return Optional.ofNullable(position)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /positions/:id -> delete the "id" position.
     */
    @RequestMapping(value = "/positions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        log.debug("REST request to delete Position : {}", id);
        positionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("position", id.toString())).build();
    }
}
