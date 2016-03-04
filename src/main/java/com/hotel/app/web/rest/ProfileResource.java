package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Profile;
import com.hotel.app.service.ProfileService;
import com.hotel.app.web.rest.util.HeaderUtil;
import com.hotel.app.web.rest.util.PaginationUtil;

import org.joda.time.DateTime;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Profile.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

	private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

	@Inject
	private ProfileService profileService;

	/**
	 * POST /profiles -> Create a new profile.
	 */
	@RequestMapping(value = "/profiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Profile> createProfile(@Valid @RequestBody Profile profile) throws URISyntaxException {
		log.debug("REST request to save Profile : {}", profile);
		if (profile.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("profile", "idexists", "A new profile cannot already have an ID"))
					.body(null);
		}
		Profile result = profileService.save(profile);
		return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("profile", result.getId().toString())).body(result);
	}

	/**
	 * PUT /profiles -> Updates an existing profile.
	 */
	@RequestMapping(value = "/profiles", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Profile> updateProfile(@Valid @RequestBody Profile profile) throws URISyntaxException {
		log.debug("REST request to update Profile : {}", profile);
		if (profile.getId() == null) {
			return createProfile(profile);
		}
		Profile result = profileService.save(profile);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("profile", profile.getId().toString()))
				.body(result);
	}

	/**
	 * GET /profiles -> get all the profiles.
	 */
	@RequestMapping(value = "/profiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Profile>> getAllProfiles(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Profiles");
		Page<Profile> page = profileService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profiles");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /profiles/:id -> get the "id" profile.
	 */
	@RequestMapping(value = "/profiles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
		log.debug("REST request to get Profile : {}", id);
		Profile profile = profileService.findOne(id);
		return Optional.ofNullable(profile).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /profiles/:id -> delete the "id" profile.
	 */
	@RequestMapping(value = "/profiles/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
		log.debug("REST request to delete Profile : {}", id);
		profileService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profile", id.toString())).build();
	}

	/**
	 * GET /profiles -> get all the profiles.
	 */
	@RequestMapping(value = "/profiles/findByMultiAttr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Profile>> findByMultiAttr(Pageable pageable,
			@RequestParam(value = "positionId", required = false, defaultValue = "0") Long positionId,
			@RequestParam(value = "departmentId", required = false, defaultValue = "0") Long departmentId,
			@RequestParam(value = "statusId", required = false, defaultValue = "0") Long statusId,
			@RequestParam(value = "fullname", required = false) String fullname) throws URISyntaxException {
		log.debug("REST request to get a page of findByMultiAttr" + fullname);
		Page<Profile> page = profileService.findByMultiAttr(pageable, positionId, departmentId, statusId, fullname);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profiles/findByMultiAttr");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/profiles/findByMultiAttrWithRanger", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Profile>> findByMultiAttrWithRanger(Pageable pageable,
			@RequestParam(value = "positionId", required = false, defaultValue = "0") Long positionId,
			@RequestParam(value = "departmentId", required = false, defaultValue = "0") Long departmentId,
			@RequestParam(value = "statusId", required = false, defaultValue = "0") Long statusId,
			@RequestParam(value = "fullname", required = false) String full_name,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws URISyntaxException {
		log.debug("REST request to get a page of findByMultiAttr" + fromDate);
		Page<Profile> page = profileService.findByMultiAttrWithRanger(pageable, positionId, departmentId, statusId,
				full_name,LocalDate.parse(fromDate), LocalDate.parse(toDate));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/profiles/findByMultiAttrWithRanger");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
}
