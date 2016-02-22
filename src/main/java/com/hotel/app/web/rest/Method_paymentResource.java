package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Method_payment;
import com.hotel.app.service.Method_paymentService;
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
 * REST controller for managing Method_payment.
 */
@RestController
@RequestMapping("/api")
public class Method_paymentResource {

    private final Logger log = LoggerFactory.getLogger(Method_paymentResource.class);
        
    @Inject
    private Method_paymentService method_paymentService;
    
    /**
     * POST  /method_payments -> Create a new method_payment.
     */
    @RequestMapping(value = "/method_payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_payment> createMethod_payment(@Valid @RequestBody Method_payment method_payment) throws URISyntaxException {
        log.debug("REST request to save Method_payment : {}", method_payment);
        if (method_payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("method_payment", "idexists", "A new method_payment cannot already have an ID")).body(null);
        }
        Method_payment result = method_paymentService.save(method_payment);
        return ResponseEntity.created(new URI("/api/method_payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("method_payment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /method_payments -> Updates an existing method_payment.
     */
    @RequestMapping(value = "/method_payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_payment> updateMethod_payment(@Valid @RequestBody Method_payment method_payment) throws URISyntaxException {
        log.debug("REST request to update Method_payment : {}", method_payment);
        if (method_payment.getId() == null) {
            return createMethod_payment(method_payment);
        }
        Method_payment result = method_paymentService.save(method_payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("method_payment", method_payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /method_payments -> get all the method_payments.
     */
    @RequestMapping(value = "/method_payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Method_payment>> getAllMethod_payments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Method_payments");
        Page<Method_payment> page = method_paymentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/method_payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /method_payments/:id -> get the "id" method_payment.
     */
    @RequestMapping(value = "/method_payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_payment> getMethod_payment(@PathVariable Long id) {
        log.debug("REST request to get Method_payment : {}", id);
        Method_payment method_payment = method_paymentService.findOne(id);
        return Optional.ofNullable(method_payment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /method_payments/:id -> delete the "id" method_payment.
     */
    @RequestMapping(value = "/method_payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMethod_payment(@PathVariable Long id) {
        log.debug("REST request to delete Method_payment : {}", id);
        method_paymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("method_payment", id.toString())).build();
    }
}
