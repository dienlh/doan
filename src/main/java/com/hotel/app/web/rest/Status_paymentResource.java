package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_payment;
import com.hotel.app.service.Status_paymentService;
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
 * REST controller for managing Status_payment.
 */
@RestController
@RequestMapping("/api")
public class Status_paymentResource {

    private final Logger log = LoggerFactory.getLogger(Status_paymentResource.class);
        
    @Inject
    private Status_paymentService status_paymentService;
    
    /**
     * POST  /status_payments -> Create a new status_payment.
     */
    @RequestMapping(value = "/status_payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_payment> createStatus_payment(@Valid @RequestBody Status_payment status_payment) throws URISyntaxException {
        log.debug("REST request to save Status_payment : {}", status_payment);
        if (status_payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_payment", "idexists", "A new status_payment cannot already have an ID")).body(null);
        }
        Status_payment result = status_paymentService.save(status_payment);
        return ResponseEntity.created(new URI("/api/status_payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_payment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_payments -> Updates an existing status_payment.
     */
    @RequestMapping(value = "/status_payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_payment> updateStatus_payment(@Valid @RequestBody Status_payment status_payment) throws URISyntaxException {
        log.debug("REST request to update Status_payment : {}", status_payment);
        if (status_payment.getId() == null) {
            return createStatus_payment(status_payment);
        }
        Status_payment result = status_paymentService.save(status_payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_payment", status_payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_payments -> get all the status_payments.
     */
    @RequestMapping(value = "/status_payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_payment>> getAllStatus_payments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_payments");
        Page<Status_payment> page = status_paymentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_payments/:id -> get the "id" status_payment.
     */
    @RequestMapping(value = "/status_payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_payment> getStatus_payment(@PathVariable Long id) {
        log.debug("REST request to get Status_payment : {}", id);
        Status_payment status_payment = status_paymentService.findOne(id);
        return Optional.ofNullable(status_payment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_payments/:id -> delete the "id" status_payment.
     */
    @RequestMapping(value = "/status_payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_payment(@PathVariable Long id) {
        log.debug("REST request to delete Status_payment : {}", id);
        status_paymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_payment", id.toString())).build();
    }
}
