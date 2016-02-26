package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Services;
import com.hotel.app.service.ServicesService;
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
 * REST controller for managing Services.
 */
@RestController
@RequestMapping("/api")
public class ServicesResource {

    private final Logger log = LoggerFactory.getLogger(ServicesResource.class);
        
    @Inject
    private ServicesService servicesService;
    
    /**
     * POST  /servicess -> Create a new services.
     */
    @RequestMapping(value = "/servicess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Services> createServices(@Valid @RequestBody Services services) throws URISyntaxException {
        log.debug("REST request to save Services : {}", services);
        if (services.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("services", "idexists", "A new services cannot already have an ID")).body(null);
        }
        Services result = servicesService.save(services);
        return ResponseEntity.created(new URI("/api/servicess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("services", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicess -> Updates an existing services.
     */
    @RequestMapping(value = "/servicess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Services> updateServices(@Valid @RequestBody Services services) throws URISyntaxException {
        log.debug("REST request to update Services : {}", services);
        if (services.getId() == null) {
            return createServices(services);
        }
        Services result = servicesService.save(services);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("services", services.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicess -> get all the servicess.
     */
    @RequestMapping(value = "/servicess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Services>> getAllServicess(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Servicess");
        Page<Services> page = servicesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/servicess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /servicess/:id -> get the "id" services.
     */
    @RequestMapping(value = "/servicess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Services> getServices(@PathVariable Long id) {
        log.debug("REST request to get Services : {}", id);
        Services services = servicesService.findOne(id);
        return Optional.ofNullable(services)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /servicess/:id -> delete the "id" services.
     */
    @RequestMapping(value = "/servicess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteServices(@PathVariable Long id) {
        log.debug("REST request to delete Services : {}", id);
        servicesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("services", id.toString())).build();
    }
}
