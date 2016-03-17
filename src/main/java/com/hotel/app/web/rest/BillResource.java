package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Bill;
import com.hotel.app.service.BillService;
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
 * REST controller for managing Bill.
 */
@RestController
@RequestMapping("/api")
public class BillResource {

	private final Logger log = LoggerFactory.getLogger(BillResource.class);

	@Inject
	private BillService billService;

	/**
	 * POST /bills -> Create a new bill.
	 */
	@RequestMapping(value = "/bills", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill> createBill(@Valid @RequestBody Bill bill) throws URISyntaxException {
		log.debug("REST request to save Bill : {}", bill);
		if (bill.getId() != null) {
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert("bill", "idexists", "A new bill cannot already have an ID"))
					.body(null);
		}
		Bill result = billService.save(bill);
		return ResponseEntity.created(new URI("/api/bills/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("bill", result.getId().toString())).body(result);
	}

	/**
	 * PUT /bills -> Updates an existing bill.
	 */
	@RequestMapping(value = "/bills", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill> updateBill(@Valid @RequestBody Bill bill) throws URISyntaxException {
		log.debug("REST request to update Bill : {}", bill);
		if (bill.getId() == null) {
			return createBill(bill);
		}
		Bill result = billService.save(bill);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("bill", bill.getId().toString()))
				.body(result);
	}

	/**
	 * GET /bills -> get all the bills.
	 */
	@RequestMapping(value = "/bills", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Bill>> getAllBills(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Bills");
		Page<Bill> page = billService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bills");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /bills/:id -> get the "id" bill.
	 */
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill> getBill(@PathVariable Long id) {
		log.debug("REST request to get Bill : {}", id);
		Bill bill = billService.findOne(id);
		return Optional.ofNullable(bill).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /bills/:id -> delete the "id" bill.
	 */
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
		log.debug("REST request to delete Bill : {}", id);
		billService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bill", id.toString())).build();
	}

	@RequestMapping(value = "/bills/findOneByReservationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill> findOneByReservationId(@RequestParam(value="reservationId" , required=true) Long reservationId) {
		log.debug("REST request to get Bill : {}", reservationId);
		Bill bill = billService.findOneByReservationId(reservationId);
		return Optional.ofNullable(bill).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
