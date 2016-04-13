package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Status_profile;
import com.hotel.app.service.Status_profileService;
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
 * REST controller for managing Status_profile.
 */
@RestController
@RequestMapping("/api")
public class Status_profileResource {

    private final Logger log = LoggerFactory.getLogger(Status_profileResource.class);
        
    @Inject
    private Status_profileService status_profileService;
    
    /**
     * POST  /status_profiles -> Create a new status_profile.
     */
    @RequestMapping(value = "/status_profiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_profile> createStatus_profile(@Valid @RequestBody Status_profile status_profile) throws URISyntaxException {
        log.debug("REST request to save Status_profile : {}", status_profile);
        if (status_profile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status_profile", "idexists", "A new status_profile cannot already have an ID")).body(null);
        }
        Status_profile result = status_profileService.save(status_profile);
        return ResponseEntity.created(new URI("/api/status_profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_profile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_profiles -> Updates an existing status_profile.
     */
    @RequestMapping(value = "/status_profiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_profile> updateStatus_profile(@Valid @RequestBody Status_profile status_profile) throws URISyntaxException {
        log.debug("REST request to update Status_profile : {}", status_profile);
        if (status_profile.getId() == null) {
            return createStatus_profile(status_profile);
        }
        Status_profile result = status_profileService.save(status_profile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_profile", status_profile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_profiles -> get all the status_profiles.
     */
    @RequestMapping(value = "/status_profiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_profile>> getAllStatus_profiles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Status_profiles");
        Page<Status_profile> page = status_profileService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_profiles/:id -> get the "id" status_profile.
     */
    @RequestMapping(value = "/status_profiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_profile> getStatus_profile(@PathVariable Long id) {
        log.debug("REST request to get Status_profile : {}", id);
        Status_profile status_profile = status_profileService.findOne(id);
        return Optional.ofNullable(status_profile)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_profiles/:id -> delete the "id" status_profile.
     */
    @RequestMapping(value = "/status_profiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_profile(@PathVariable Long id) {
        log.debug("REST request to delete Status_profile : {}", id);
        status_profileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_profile", id.toString())).build();
    }
}
