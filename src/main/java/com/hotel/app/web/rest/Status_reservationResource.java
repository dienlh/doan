package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_reservation;
import com.hotel.app.service.Status_reservationService;
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
 * REST controller for managing Status_reservation.
 */
@RestController
@RequestMapping("/api")
public class Status_reservationResource {

    private final Logger log = LoggerFactory.getLogger(Status_reservationResource.class);
        
    @Inject
    private Status_reservationService status_reservationService;
    
    /**
     * POST  /status_reservations -> Create a new status_reservation.
     */
    @RequestMapping(value = "/status_reservations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_reservation> createStatus_reservation(@Valid @RequestBody Status_reservation status_reservation) throws URISyntaxException {
        log.debug("REST request to save Status_reservation : {}", status_reservation);
        if (status_reservation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_reservation", "idexists", "A new status_reservation cannot already have an ID")).body(null);
        }
        Status_reservation result = status_reservationService.save(status_reservation);
        return ResponseEntity.created(new URI("/api/status_reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_reservation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_reservations -> Updates an existing status_reservation.
     */
    @RequestMapping(value = "/status_reservations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_reservation> updateStatus_reservation(@Valid @RequestBody Status_reservation status_reservation) throws URISyntaxException {
        log.debug("REST request to update Status_reservation : {}", status_reservation);
        if (status_reservation.getId() == null) {
            return createStatus_reservation(status_reservation);
        }
        Status_reservation result = status_reservationService.save(status_reservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_reservation", status_reservation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_reservations -> get all the status_reservations.
     */
    @RequestMapping(value = "/status_reservations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_reservation>> getAllStatus_reservations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_reservations");
        Page<Status_reservation> page = status_reservationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_reservations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_reservations/:id -> get the "id" status_reservation.
     */
    @RequestMapping(value = "/status_reservations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_reservation> getStatus_reservation(@PathVariable Long id) {
        log.debug("REST request to get Status_reservation : {}", id);
        Status_reservation status_reservation = status_reservationService.findOne(id);
        return Optional.ofNullable(status_reservation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_reservations/:id -> delete the "id" status_reservation.
     */
    @RequestMapping(value = "/status_reservations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_reservation(@PathVariable Long id) {
        log.debug("REST request to delete Status_reservation : {}", id);
        status_reservationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_reservation", id.toString())).build();
    }
}
