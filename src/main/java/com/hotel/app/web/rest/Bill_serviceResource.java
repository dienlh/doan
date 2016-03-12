package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.service.Bill_serviceService;
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
 * REST controller for managing Bill_service.
 */
@RestController
@RequestMapping("/api")
public class Bill_serviceResource {

	private final Logger log = LoggerFactory.getLogger(Bill_serviceResource.class);

	@Inject
	private Bill_serviceService bill_serviceService;

	/**
	 * POST /bill_services -> Create a new bill_service.
	 */
	@RequestMapping(value = "/bill_services", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill_service> createBill_service(@Valid @RequestBody Bill_service bill_service)
			throws URISyntaxException {
		log.debug("REST request to save Bill_service : {}", bill_service);
		if (bill_service.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bill_service", "idexists",
					"A new bill_service cannot already have an ID")).body(null);
		}
		Bill_service result = bill_serviceService.save(bill_service);
		return ResponseEntity.created(new URI("/api/bill_services/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("bill_service", result.getId().toString())).body(result);
	}

	/**
	 * PUT /bill_services -> Updates an existing bill_service.
	 */
	@RequestMapping(value = "/bill_services", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill_service> updateBill_service(@Valid @RequestBody Bill_service bill_service)
			throws URISyntaxException {
		log.debug("REST request to update Bill_service : {}", bill_service);
		if (bill_service.getId() == null) {
			return createBill_service(bill_service);
		}
		Bill_service result = bill_serviceService.save(bill_service);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("bill_service", bill_service.getId().toString()))
				.body(result);
	}

	/**
	 * GET /bill_services -> get all the bill_services.
	 */
	@RequestMapping(value = "/bill_services", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Bill_service>> getAllBill_services(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Bill_services");
		Page<Bill_service> page = bill_serviceService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bill_services");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /bill_services/:id -> get the "id" bill_service.
	 */
	@RequestMapping(value = "/bill_services/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill_service> getBill_service(@PathVariable Long id) {
		log.debug("REST request to get Bill_service : {}", id);
		Bill_service bill_service = bill_serviceService.findOne(id);
		return Optional.ofNullable(bill_service).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /bill_services/:id -> delete the "id" bill_service.
	 */
	@RequestMapping(value = "/bill_services/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteBill_service(@PathVariable Long id) {
		log.debug("REST request to delete Bill_service : {}", id);
		bill_serviceService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bill_service", id.toString())).build();
	}

	@RequestMapping(value = "/bill_services/findAllByMultiAttr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Bill_service>> findAllByMultiAttr(Pageable pageable,
			@RequestParam(value = "serviceId", required = false, defaultValue = "0") Long serviceId,
			@RequestParam(value = "statusId", required = false, defaultValue = "0") Long statusId,
			@RequestParam(value = "roomId", required = false, defaultValue = "0") Long roomId)
			throws URISyntaxException {
		log.debug("REST request to get a page of Bill_services");
		Page<Bill_service> page = bill_serviceService.findAllByMultiAttr(pageable, serviceId, statusId, roomId);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/bill_services/findAllByMultiAttr");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	// @RequestMapping(value = "/bill_services/findAllByMultiAttr", method =
	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @Timed
	// public ResponseEntity<List<Bill_service>> findAllByMultiAttr(Pageable
	// pageable,
	// @RequestParam(value = "serviceId", required = false,defaultValue="0")
	// Long serviceId,
	// @RequestParam(value="statusId",required=false,defaultValue="0") Long
	// statusId,
	// @RequestParam(value="roomId",required=false,defaultValue="0") Long
	// roomId)
	// throws URISyntaxException {
	// log.debug("REST request to get a page of Bill_services");
	// Page<Bill_service> page =
	// bill_serviceService.findAllByMultiAttr(pageable, serviceId, statusId,
	// roomId);
	// HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
	// "/api/bill_services/");
	// return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	// }
}
