package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Funiture;
import com.hotel.app.service.FunitureService;
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
 * REST controller for managing Funiture.
 */
@RestController
@RequestMapping("/api")
public class FunitureResource {

    private final Logger log = LoggerFactory.getLogger(FunitureResource.class);
        
    @Inject
    private FunitureService funitureService;
    
    /**
     * POST  /funitures -> Create a new funiture.
     */
    @RequestMapping(value = "/funitures",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Funiture> createFuniture(@Valid @RequestBody Funiture funiture) throws URISyntaxException {
        log.debug("REST request to save Funiture : {}", funiture);
        if (funiture.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("funiture", "idexists", "A new funiture cannot already have an ID")).body(null);
        }
        Funiture result = funitureService.save(funiture);
        return ResponseEntity.created(new URI("/api/funitures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("funiture", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /funitures -> Updates an existing funiture.
     */
    @RequestMapping(value = "/funitures",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Funiture> updateFuniture(@Valid @RequestBody Funiture funiture) throws URISyntaxException {
        log.debug("REST request to update Funiture : {}", funiture);
        if (funiture.getId() == null) {
            return createFuniture(funiture);
        }
        Funiture result = funitureService.save(funiture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("funiture", funiture.getId().toString()))
            .body(result);
    }

    /**
     * GET  /funitures -> get all the funitures.
     */
    @RequestMapping(value = "/funitures",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Funiture>> getAllFunitures(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Funitures");
        Page<Funiture> page = funitureService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/funitures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /funitures/:id -> get the "id" funiture.
     */
    @RequestMapping(value = "/funitures/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Funiture> getFuniture(@PathVariable Long id) {
        log.debug("REST request to get Funiture : {}", id);
        Funiture funiture = funitureService.findOne(id);
        return Optional.ofNullable(funiture)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /funitures/:id -> delete the "id" funiture.
     */
    @RequestMapping(value = "/funitures/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFuniture(@PathVariable Long id) {
        log.debug("REST request to delete Funiture : {}", id);
        funitureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("funiture", id.toString())).build();
    }
}
