package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Policy;
import com.hotel.app.service.PolicyService;
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
 * REST controller for managing Policy.
 */
@RestController
@RequestMapping("/api")
public class PolicyResource {

    private final Logger log = LoggerFactory.getLogger(PolicyResource.class);
        
    @Inject
    private PolicyService policyService;
    
    /**
     * POST  /policys -> Create a new policy.
     */
    @RequestMapping(value = "/policys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody Policy policy) throws URISyntaxException {
        log.debug("REST request to save Policy : {}", policy);
        if (policy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("policy", "idexists", "A new policy cannot already have an ID")).body(null);
        }
        Policy result = policyService.save(policy);
        return ResponseEntity.created(new URI("/api/policys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("policy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policys -> Updates an existing policy.
     */
    @RequestMapping(value = "/policys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Policy> updatePolicy(@Valid @RequestBody Policy policy) throws URISyntaxException {
        log.debug("REST request to update Policy : {}", policy);
        if (policy.getId() == null) {
            return createPolicy(policy);
        }
        Policy result = policyService.save(policy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("policy", policy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policys -> get all the policys.
     */
    @RequestMapping(value = "/policys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Policy>> getAllPolicys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Policys");
        Page<Policy> page = policyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/policys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /policys/:id -> get the "id" policy.
     */
    @RequestMapping(value = "/policys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Policy> getPolicy(@PathVariable Long id) {
        log.debug("REST request to get Policy : {}", id);
        Policy policy = policyService.findOne(id);
        return Optional.ofNullable(policy)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /policys/:id -> delete the "id" policy.
     */
    @RequestMapping(value = "/policys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        log.debug("REST request to delete Policy : {}", id);
        policyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("policy", id.toString())).build();
    }
}
