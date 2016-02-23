package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Method_booking;
import com.hotel.app.service.Method_bookingService;
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
 * REST controller for managing Method_booking.
 */
@RestController
@RequestMapping("/api")
public class Method_bookingResource {

    private final Logger log = LoggerFactory.getLogger(Method_bookingResource.class);
        
    @Inject
    private Method_bookingService method_bookingService;
    
    /**
     * POST  /method_bookings -> Create a new method_booking.
     */
    @RequestMapping(value = "/method_bookings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_booking> createMethod_booking(@Valid @RequestBody Method_booking method_booking) throws URISyntaxException {
        log.debug("REST request to save Method_booking : {}", method_booking);
        if (method_booking.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("method_booking", "idexists", "A new method_booking cannot already have an ID")).body(null);
        }
        Method_booking result = method_bookingService.save(method_booking);
        return ResponseEntity.created(new URI("/api/method_bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("method_booking", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /method_bookings -> Updates an existing method_booking.
     */
    @RequestMapping(value = "/method_bookings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_booking> updateMethod_booking(@Valid @RequestBody Method_booking method_booking) throws URISyntaxException {
        log.debug("REST request to update Method_booking : {}", method_booking);
        if (method_booking.getId() == null) {
            return createMethod_booking(method_booking);
        }
        Method_booking result = method_bookingService.save(method_booking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("method_booking", method_booking.getId().toString()))
            .body(result);
    }

    /**
     * GET  /method_bookings -> get all the method_bookings.
     */
    @RequestMapping(value = "/method_bookings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Method_booking>> getAllMethod_bookings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Method_bookings");
        Page<Method_booking> page = method_bookingService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/method_bookings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /method_bookings/:id -> get the "id" method_booking.
     */
    @RequestMapping(value = "/method_bookings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_booking> getMethod_booking(@PathVariable Long id) {
        log.debug("REST request to get Method_booking : {}", id);
        Method_booking method_booking = method_bookingService.findOne(id);
        return Optional.ofNullable(method_booking)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /method_bookings/:id -> delete the "id" method_booking.
     */
    @RequestMapping(value = "/method_bookings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMethod_booking(@PathVariable Long id) {
        log.debug("REST request to delete Method_booking : {}", id);
        method_bookingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("method_booking", id.toString())).build();
    }
}
