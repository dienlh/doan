package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Method_register;
import com.hotel.app.service.Method_registerService;
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
 * REST controller for managing Method_register.
 */
@RestController
@RequestMapping("/api")
public class Method_registerResource {

    private final Logger log = LoggerFactory.getLogger(Method_registerResource.class);
        
    @Inject
    private Method_registerService method_registerService;
    
    /**
     * POST  /method_registers -> Create a new method_register.
     */
    @RequestMapping(value = "/method_registers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_register> createMethod_register(@Valid @RequestBody Method_register method_register) throws URISyntaxException {
        log.debug("REST request to save Method_register : {}", method_register);
        if (method_register.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("method_register", "idexists", "A new method_register cannot already have an ID")).body(null);
        }
        Method_register result = method_registerService.save(method_register);
        return ResponseEntity.created(new URI("/api/method_registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("method_register", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /method_registers -> Updates an existing method_register.
     */
    @RequestMapping(value = "/method_registers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_register> updateMethod_register(@Valid @RequestBody Method_register method_register) throws URISyntaxException {
        log.debug("REST request to update Method_register : {}", method_register);
        if (method_register.getId() == null) {
            return createMethod_register(method_register);
        }
        Method_register result = method_registerService.save(method_register);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("method_register", method_register.getId().toString()))
            .body(result);
    }

    /**
     * GET  /method_registers -> get all the method_registers.
     */
    @RequestMapping(value = "/method_registers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Method_register>> getAllMethod_registers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Method_registers");
        Page<Method_register> page = method_registerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/method_registers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /method_registers/:id -> get the "id" method_register.
     */
    @RequestMapping(value = "/method_registers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Method_register> getMethod_register(@PathVariable Long id) {
        log.debug("REST request to get Method_register : {}", id);
        Method_register method_register = method_registerService.findOne(id);
        return Optional.ofNullable(method_register)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /method_registers/:id -> delete the "id" method_register.
     */
    @RequestMapping(value = "/method_registers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMethod_register(@PathVariable Long id) {
        log.debug("REST request to delete Method_register : {}", id);
        method_registerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("method_register", id.toString())).build();
    }
}
