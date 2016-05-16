package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Bill;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.domain.Book;
import com.hotel.app.domain.Register_info;
import com.hotel.app.domain.Status_bill;
import com.hotel.app.domain.Status_bill_service;
import com.hotel.app.service.BillExcelBuilder;
import com.hotel.app.service.BillPDFBuilder;
import com.hotel.app.service.BillService;
import com.hotel.app.service.Bill_servicePDFBuilder;
import com.hotel.app.service.Bill_serviceService;
import com.hotel.app.service.PDFBuilder;
import com.hotel.app.service.Register_infoExcelBuilder;
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
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

	@Inject
	private Bill_serviceService bill_serviceService;
	
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
	public ResponseEntity<Bill> findOneByReservationId(
			@RequestParam(value = "reservationId", required = true) Long reservationId) {
		log.debug("REST request to get Bill : {}", reservationId);
		Bill bill = billService.findOneByReservationId(reservationId);
		return Optional.ofNullable(bill).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping(value = "/bills/createByReservationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Bill> createByReservationId(
			@RequestParam(value = "reservationId", required = true) Long reservationId) {
		log.debug("REST request to create Bill by reservation id: {}", reservationId);
		List<Bill_service> bill_services = bill_serviceService.findAllByReservationIdAndStatus(reservationId, 5L);
		Bill bill = new Bill();
		if(bill_services.isEmpty()){
			bill = billService.createByReservationId(reservationId);
		}
		return Optional.ofNullable(bill).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping(value = "/bills/exportPDF/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView exportPDF(@PathVariable Long id) {
		// create some sample data
		Bill bill = billService.findOne(id);
		Status_bill status_bill = new Status_bill();
		status_bill.setId(4L);
		bill.setStatus_bill(status_bill);
		List<Bill_service> bill_services = bill_serviceService.findAllByReservationId(bill.getReservation().getId());
		Status_bill_service status_bill_service = new Status_bill_service();
		status_bill_service.setId(4L);
		for (Bill_service bill_service : bill_services) {
			if(bill_service.getStatus_bill_service().getId()==3L){
				bill_service.setStatus_bill_service(status_bill_service);
				bill_serviceService.save(bill_service);
			}
		}
		bill=billService.save(bill);
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView(new BillPDFBuilder(), "bill", bill);
	}

	@RequestMapping(value = "/bills/findAllByMultiAttr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Bill>> findAllByMultiAttr(Pageable pageable,
			@RequestParam(value = "room", required = true) Long room,
			@RequestParam(value = "customer", required = true) Long customer,
			@RequestParam(value = "method_payment", required = true) Long method_payment,
			@RequestParam(value = "status_payment", required = true) Long status_payment,
			@RequestParam(value = "method_register", required = true) Long method_register,
			@RequestParam(value = "status_bill", required = true) Long status_bill,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws URISyntaxException {
		log.debug("REST request to get a page of Register_infos");

		if (fromDate == null && toDate == null) {
			Page<Bill> page = billService.findAllByMultiAttr(pageable, room, customer, method_payment, status_payment,
					method_register, status_bill);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bills/findAllByMultiAttr");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		} else if (fromDate != null && toDate != null) {
			Page<Bill> page = billService.findAllByMultiAttr(pageable, room, customer, method_payment, status_payment,
					method_register, status_bill, ZonedDateTime.parse(fromDate + "T00:00:00+07:00"),
					ZonedDateTime.parse(toDate + "T23:59:59+07:00"));
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bills/findAllByMultiAttr");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bills/exportExcel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ModelAndView exportExcel(Pageable pageable, @RequestParam(value = "room", required = true) Long room,
			@RequestParam(value = "customer", required = true) Long customer,
			@RequestParam(value = "method_payment", required = true) Long method_payment,
			@RequestParam(value = "status_payment", required = true) Long status_payment,
			@RequestParam(value = "method_register", required = true) Long method_register,
			@RequestParam(value = "status_bill", required = true) Long status_bill,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws URISyntaxException {

		log.debug("Start exportExcel ");
		List<Bill> bills = new ArrayList<>();
		if ((fromDate == null && toDate == null) || (fromDate.equals("undefined") && toDate.equals("undefined"))) {
			bills = billService.findAllByMultiAttr(room, customer, method_payment, status_payment, method_register,
					status_bill);
		} else if (fromDate != null && toDate != null) {
			bills = billService.findAllByMultiAttr(room, customer, method_payment, status_payment,
					method_register, status_bill, ZonedDateTime.parse(fromDate + "T00:00:00+07:00"),
					ZonedDateTime.parse(toDate + "T23:59:59+07:00"));
		} else {
			return null;
		}
		return new ModelAndView(new BillExcelBuilder(), "lists", bills);
	}
}
