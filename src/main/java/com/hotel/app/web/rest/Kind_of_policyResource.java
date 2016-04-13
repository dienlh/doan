package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Kind_of_policy;
import com.hotel.app.service.Kind_of_policyService;
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
 * REST controller for managing Kind_of_policy.
 */
@RestController
@RequestMapping("/api")
public class Kind_of_policyResource {

    private final Logger log = LoggerFactory.getLogger(Kind_of_policyResource.class);
        
    @Inject
    private Kind_of_policyService kind_of_policyService;
    
    /**
     * POST  /kind_of_policys -> Create a new kind_of_policy.
     */
    @RequestMapping(value = "/kind_of_policys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kind_of_policy> createKind_of_policy(@Valid @RequestBody Kind_of_policy kind_of_policy) throws URISyntaxException {
        log.debug("REST request to save Kind_of_policy : {}", kind_of_policy);
        if (kind_of_policy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kind_of_policy", "idexists", "A new kind_of_policy cannot already have an ID")).body(null);
        }
        Kind_of_policy result = kind_of_policyService.save(kind_of_policy);
        return ResponseEntity.created(new URI("/api/kind_of_policys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kind_of_policy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kind_of_policys -> Updates an existing kind_of_policy.
     */
    @RequestMapping(value = "/kind_of_policys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kind_of_policy> updateKind_of_policy(@Valid @RequestBody Kind_of_policy kind_of_policy) throws URISyntaxException {
        log.debug("REST request to update Kind_of_policy : {}", kind_of_policy);
        if (kind_of_policy.getId() == null) {
            return createKind_of_policy(kind_of_policy);
        }
        Kind_of_policy result = kind_of_policyService.save(kind_of_policy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kind_of_policy", kind_of_policy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kind_of_policys -> get all the kind_of_policys.
     */
    @RequestMapping(value = "/kind_of_policys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Kind_of_policy>> getAllKind_of_policys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Kind_of_policys");
        Page<Kind_of_policy> page = kind_of_policyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kind_of_policys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /kind_of_policys/:id -> get the "id" kind_of_policy.
     */
    @RequestMapping(value = "/kind_of_policys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kind_of_policy> getKind_of_policy(@PathVariable Long id) {
        log.debug("REST request to get Kind_of_policy : {}", id);
        Kind_of_policy kind_of_policy = kind_of_policyService.findOne(id);
        return Optional.ofNullable(kind_of_policy)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kind_of_policys/:id -> delete the "id" kind_of_policy.
     */
    @RequestMapping(value = "/kind_of_policys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKind_of_policy(@PathVariable Long id) {
        log.debug("REST request to delete Kind_of_policy : {}", id);
        kind_of_policyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kind_of_policy", id.toString())).build();
    }
}
