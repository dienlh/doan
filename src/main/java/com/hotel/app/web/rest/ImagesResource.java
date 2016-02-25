package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Images;
import com.hotel.app.service.ImagesService;
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
 * REST controller for managing Images.
 */
@RestController
@RequestMapping("/api")
public class ImagesResource {

    private final Logger log = LoggerFactory.getLogger(ImagesResource.class);
        
    @Inject
    private ImagesService imagesService;
    
    /**
     * POST  /imagess -> Create a new images.
     */
    @RequestMapping(value = "/imagess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Images> createImages(@Valid @RequestBody Images images) throws URISyntaxException {
        log.debug("REST request to save Images : {}", images);
        if (images.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("images", "idexists", "A new images cannot already have an ID")).body(null);
        }
        Images result = imagesService.save(images);
        return ResponseEntity.created(new URI("/api/imagess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("images", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imagess -> Updates an existing images.
     */
    @RequestMapping(value = "/imagess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Images> updateImages(@Valid @RequestBody Images images) throws URISyntaxException {
        log.debug("REST request to update Images : {}", images);
        if (images.getId() == null) {
            return createImages(images);
        }
        Images result = imagesService.save(images);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("images", images.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imagess -> get all the imagess.
     */
    @RequestMapping(value = "/imagess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Images>> getAllImagess(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Imagess");
        Page<Images> page = imagesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/imagess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /imagess/:id -> get the "id" images.
     */
    @RequestMapping(value = "/imagess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Images> getImages(@PathVariable Long id) {
        log.debug("REST request to get Images : {}", id);
        Images images = imagesService.findOne(id);
        return Optional.ofNullable(images)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /imagess/:id -> delete the "id" images.
     */
    @RequestMapping(value = "/imagess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteImages(@PathVariable Long id) {
        log.debug("REST request to delete Images : {}", id);
        imagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("images", id.toString())).build();
    }
}
