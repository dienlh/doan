package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_policy;
import com.hotel.app.service.Status_policyService;
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
 * REST controller for managing Status_policy.
 */
@RestController
@RequestMapping("/api")
public class Status_policyResource {

    private final Logger log = LoggerFactory.getLogger(Status_policyResource.class);
        
    @Inject
    private Status_policyService status_policyService;
    
    /**
     * POST  /status_policys -> Create a new status_policy.
     */
    @RequestMapping(value = "/status_policys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_policy> createStatus_policy(@Valid @RequestBody Status_policy status_policy) throws URISyntaxException {
        log.debug("REST request to save Status_policy : {}", status_policy);
        if (status_policy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_policy", "idexists", "A new status_policy cannot already have an ID")).body(null);
        }
        Status_policy result = status_policyService.save(status_policy);
        return ResponseEntity.created(new URI("/api/status_policys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_policy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_policys -> Updates an existing status_policy.
     */
    @RequestMapping(value = "/status_policys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_policy> updateStatus_policy(@Valid @RequestBody Status_policy status_policy) throws URISyntaxException {
        log.debug("REST request to update Status_policy : {}", status_policy);
        if (status_policy.getId() == null) {
            return createStatus_policy(status_policy);
        }
        Status_policy result = status_policyService.save(status_policy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_policy", status_policy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_policys -> get all the status_policys.
     */
    @RequestMapping(value = "/status_policys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_policy>> getAllStatus_policys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_policys");
        Page<Status_policy> page = status_policyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_policys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_policys/:id -> get the "id" status_policy.
     */
    @RequestMapping(value = "/status_policys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_policy> getStatus_policy(@PathVariable Long id) {
        log.debug("REST request to get Status_policy : {}", id);
        Status_policy status_policy = status_policyService.findOne(id);
        return Optional.ofNullable(status_policy)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_policys/:id -> delete the "id" status_policy.
     */
    @RequestMapping(value = "/status_policys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_policy(@PathVariable Long id) {
        log.debug("REST request to delete Status_policy : {}", id);
        status_policyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_policy", id.toString())).build();
    }
}
