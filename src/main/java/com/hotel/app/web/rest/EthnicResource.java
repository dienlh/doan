package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Ethnic;
import com.hotel.app.service.EthnicService;
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
 * REST controller for managing Ethnic.
 */
@RestController
@RequestMapping("/api")
public class EthnicResource {

    private final Logger log = LoggerFactory.getLogger(EthnicResource.class);
        
    @Inject
    private EthnicService ethnicService;
    
    /**
     * POST  /ethnics -> Create a new ethnic.
     */
    @RequestMapping(value = "/ethnics",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ethnic> createEthnic(@Valid @RequestBody Ethnic ethnic) throws URISyntaxException {
        log.debug("REST request to save Ethnic : {}", ethnic);
        if (ethnic.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ethnic", "idexists", "A new ethnic cannot already have an ID")).body(null);
        }
        Ethnic result = ethnicService.save(ethnic);
        return ResponseEntity.created(new URI("/api/ethnics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ethnic", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ethnics -> Updates an existing ethnic.
     */
    @RequestMapping(value = "/ethnics",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ethnic> updateEthnic(@Valid @RequestBody Ethnic ethnic) throws URISyntaxException {
        log.debug("REST request to update Ethnic : {}", ethnic);
        if (ethnic.getId() == null) {
            return createEthnic(ethnic);
        }
        Ethnic result = ethnicService.save(ethnic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ethnic", ethnic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ethnics -> get all the ethnics.
     */
    @RequestMapping(value = "/ethnics",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ethnic>> getAllEthnics(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Ethnics");
        Page<Ethnic> page = ethnicService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ethnics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ethnics/:id -> get the "id" ethnic.
     */
    @RequestMapping(value = "/ethnics/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ethnic> getEthnic(@PathVariable Long id) {
        log.debug("REST request to get Ethnic : {}", id);
        Ethnic ethnic = ethnicService.findOne(id);
        return Optional.ofNullable(ethnic)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ethnics/:id -> delete the "id" ethnic.
     */
    @RequestMapping(value = "/ethnics/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEthnic(@PathVariable Long id) {
        log.debug("REST request to delete Ethnic : {}", id);
        ethnicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ethnic", id.toString())).build();
    }
}
