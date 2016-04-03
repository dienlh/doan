package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Images;
import com.hotel.app.domain.Room;
import com.hotel.app.service.ImagesService;
import com.hotel.app.service.RoomService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.validation.Valid;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.HashSet;
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
    
    @Inject
    private RoomService roomService;
    
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
    
    
    @RequestMapping(value = "/imagess/multipleSave", method = RequestMethod.POST)
	public @ResponseBody RedirectView multipleSave(@RequestParam("file") MultipartFile[] files,
			@RequestParam("id_room") Long id_room,
			@RequestParam(value = "typeUpload", required = true) Boolean typeUpload) {
		String fileName = null;
		String msg = "";
		Room room = roomService.findOne(id_room);
		log.info("asdasd"+room.getImagess());
		HashSet<Images> hashSet= new HashSet<>();
		if (typeUpload == false) {
			hashSet.addAll(room.getImagess());
		}
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				try {

					fileName = hashCode() + files[i].getOriginalFilename();
					byte[] bytes = files[i].getBytes();
					BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(
							new File("D:/Leature/doantotnghiep/source/hotel/src/main/webapp/upload/" + fileName)));
					buffStream.write(bytes);
					buffStream.close();

					Images images = new Images();
					images.setUrl("upload/" + fileName);
					images.setCreate_date(ZonedDateTime.now());

					Images images2 = imagesService.save(images);
					hashSet.add(images2);
					msg += "You have successfully uploaded " + fileName + "<br/>";
				} catch (Exception e) {
					return new RedirectView(
							"/hotel/#/app/room/" + id_room + "/upload?message=exception");
				}
			}
			room.setImagess(hashSet);
			roomService.save(room);
			return new RedirectView("/hotel/#/app/room/" + id_room + "/upload?message=success");
		} else {
			return new RedirectView("/hotel/#/app/room/" + id_room + "/" + id_room + "/upload?message=failed");
		}
	}
}
