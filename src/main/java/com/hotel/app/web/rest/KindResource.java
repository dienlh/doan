package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Kind;
import com.hotel.app.service.KindService;
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
 * REST controller for managing Kind.
 */
@RestController
@RequestMapping("/api")
public class KindResource {

    private final Logger log = LoggerFactory.getLogger(KindResource.class);
        
    @Inject
    private KindService kindService;
    
    /**
     * POST  /kinds -> Create a new kind.
     */
    @RequestMapping(value = "/kinds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kind> createKind(@Valid @RequestBody Kind kind) throws URISyntaxException {
        log.debug("REST request to save Kind : {}", kind);
        if (kind.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kind", "idexists", "A new kind cannot already have an ID")).body(null);
        }
        Kind result = kindService.save(kind);
        return ResponseEntity.created(new URI("/api/kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kind", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kinds -> Updates an existing kind.
     */
    @RequestMapping(value = "/kinds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kind> updateKind(@Valid @RequestBody Kind kind) throws URISyntaxException {
        log.debug("REST request to update Kind : {}", kind);
        if (kind.getId() == null) {
            return createKind(kind);
        }
        Kind result = kindService.save(kind);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kind", kind.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kinds -> get all the kinds.
     */
    @RequestMapping(value = "/kinds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Kind>> getAllKinds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Kinds");
        Page<Kind> page = kindService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kinds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /kinds/:id -> get the "id" kind.
     */
    @RequestMapping(value = "/kinds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kind> getKind(@PathVariable Long id) {
        log.debug("REST request to get Kind : {}", id);
        Kind kind = kindService.findOne(id);
        return Optional.ofNullable(kind)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kinds/:id -> delete the "id" kind.
     */
    @RequestMapping(value = "/kinds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKind(@PathVariable Long id) {
        log.debug("REST request to delete Kind : {}", id);
        kindService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kind", id.toString())).build();
    }
}
