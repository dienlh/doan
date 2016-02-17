package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Came_component;
import com.hotel.app.service.Came_componentService;
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
 * REST controller for managing Came_component.
 */
@RestController
@RequestMapping("/api")
public class Came_componentResource {

    private final Logger log = LoggerFactory.getLogger(Came_componentResource.class);
        
    @Inject
    private Came_componentService came_componentService;
    
    /**
     * POST  /came_components -> Create a new came_component.
     */
    @RequestMapping(value = "/came_components",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Came_component> createCame_component(@Valid @RequestBody Came_component came_component) throws URISyntaxException {
        log.debug("REST request to save Came_component : {}", came_component);
        if (came_component.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("came_component", "idexists", "A new came_component cannot already have an ID")).body(null);
        }
        Came_component result = came_componentService.save(came_component);
        return ResponseEntity.created(new URI("/api/came_components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("came_component", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /came_components -> Updates an existing came_component.
     */
    @RequestMapping(value = "/came_components",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Came_component> updateCame_component(@Valid @RequestBody Came_component came_component) throws URISyntaxException {
        log.debug("REST request to update Came_component : {}", came_component);
        if (came_component.getId() == null) {
            return createCame_component(came_component);
        }
        Came_component result = came_componentService.save(came_component);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("came_component", came_component.getId().toString()))
            .body(result);
    }

    /**
     * GET  /came_components -> get all the came_components.
     */
    @RequestMapping(value = "/came_components",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Came_component>> getAllCame_components(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Came_components");
        Page<Came_component> page = came_componentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/came_components");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /came_components/:id -> get the "id" came_component.
     */
    @RequestMapping(value = "/came_components/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Came_component> getCame_component(@PathVariable Long id) {
        log.debug("REST request to get Came_component : {}", id);
        Came_component came_component = came_componentService.findOne(id);
        return Optional.ofNullable(came_component)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /came_components/:id -> delete the "id" came_component.
     */
    @RequestMapping(value = "/came_components/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCame_component(@PathVariable Long id) {
        log.debug("REST request to delete Came_component : {}", id);
        came_componentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("came_component", id.toString())).build();
    }
}
