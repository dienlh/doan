package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Type_policy;
import com.hotel.app.service.Type_policyService;
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
 * REST controller for managing Type_policy.
 */
@RestController
@RequestMapping("/api")
public class Type_policyResource {

    private final Logger log = LoggerFactory.getLogger(Type_policyResource.class);
        
    @Inject
    private Type_policyService type_policyService;
    
    /**
     * POST  /type_policys -> Create a new type_policy.
     */
    @RequestMapping(value = "/type_policys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_policy> createType_policy(@Valid @RequestBody Type_policy type_policy) throws URISyntaxException {
        log.debug("REST request to save Type_policy : {}", type_policy);
        if (type_policy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("type_policy", "idexists", "A new type_policy cannot already have an ID")).body(null);
        }
        Type_policy result = type_policyService.save(type_policy);
        return ResponseEntity.created(new URI("/api/type_policys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("type_policy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type_policys -> Updates an existing type_policy.
     */
    @RequestMapping(value = "/type_policys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_policy> updateType_policy(@Valid @RequestBody Type_policy type_policy) throws URISyntaxException {
        log.debug("REST request to update Type_policy : {}", type_policy);
        if (type_policy.getId() == null) {
            return createType_policy(type_policy);
        }
        Type_policy result = type_policyService.save(type_policy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("type_policy", type_policy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type_policys -> get all the type_policys.
     */
    @RequestMapping(value = "/type_policys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Type_policy>> getAllType_policys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Type_policys");
        Page<Type_policy> page = type_policyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type_policys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type_policys/:id -> get the "id" type_policy.
     */
    @RequestMapping(value = "/type_policys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_policy> getType_policy(@PathVariable Long id) {
        log.debug("REST request to get Type_policy : {}", id);
        Type_policy type_policy = type_policyService.findOne(id);
        return Optional.ofNullable(type_policy)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type_policys/:id -> delete the "id" type_policy.
     */
    @RequestMapping(value = "/type_policys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteType_policy(@PathVariable Long id) {
        log.debug("REST request to delete Type_policy : {}", id);
        type_policyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("type_policy", id.toString())).build();
    }
}
