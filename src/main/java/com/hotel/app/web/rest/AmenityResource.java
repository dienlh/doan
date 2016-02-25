package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Amenity;
import com.hotel.app.service.AmenityService;
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
 * REST controller for managing Amenity.
 */
@RestController
@RequestMapping("/api")
public class AmenityResource {

    private final Logger log = LoggerFactory.getLogger(AmenityResource.class);
        
    @Inject
    private AmenityService amenityService;
    
    /**
     * POST  /amenitys -> Create a new amenity.
     */
    @RequestMapping(value = "/amenitys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Amenity> createAmenity(@Valid @RequestBody Amenity amenity) throws URISyntaxException {
        log.debug("REST request to save Amenity : {}", amenity);
        if (amenity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("amenity", "idexists", "A new amenity cannot already have an ID")).body(null);
        }
        Amenity result = amenityService.save(amenity);
        return ResponseEntity.created(new URI("/api/amenitys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("amenity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amenitys -> Updates an existing amenity.
     */
    @RequestMapping(value = "/amenitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Amenity> updateAmenity(@Valid @RequestBody Amenity amenity) throws URISyntaxException {
        log.debug("REST request to update Amenity : {}", amenity);
        if (amenity.getId() == null) {
            return createAmenity(amenity);
        }
        Amenity result = amenityService.save(amenity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("amenity", amenity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amenitys -> get all the amenitys.
     */
    @RequestMapping(value = "/amenitys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Amenity>> getAllAmenitys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Amenitys");
        Page<Amenity> page = amenityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amenitys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amenitys/:id -> get the "id" amenity.
     */
    @RequestMapping(value = "/amenitys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Amenity> getAmenity(@PathVariable Long id) {
        log.debug("REST request to get Amenity : {}", id);
        Amenity amenity = amenityService.findOne(id);
        return Optional.ofNullable(amenity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /amenitys/:id -> delete the "id" amenity.
     */
    @RequestMapping(value = "/amenitys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        log.debug("REST request to delete Amenity : {}", id);
        amenityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("amenity", id.toString())).build();
    }
}
