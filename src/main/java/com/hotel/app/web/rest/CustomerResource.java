package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Customer;
import com.hotel.app.service.CustomerService;
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
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

	private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

	@Inject
	private CustomerService customerService;

	/**
	 * POST /customers -> Create a new customer.
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws URISyntaxException {
		log.debug("REST request to save Customer : {}", customer);
		if (customer.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("customer", "idexists", "A new customer cannot already have an ID"))
					.body(null);
		}
		if (customerService.findByIcPassPortNumber(customer.getIc_passport_number())!=null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customer", "idexists",
					"A new customer cannot already have an email or ic_passport")).body(null);
		}
		Customer result = customerService.save(customer);
		return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("customer", result.getId().toString())).body(result);
	}

	/**
	 * PUT /customers -> Updates an existing customer.
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) throws URISyntaxException {
		log.debug("REST request to update Customer : {}", customer);
		if (customer.getId() == null) {
			return createCustomer(customer);
		}
		if (customerService.findByIcPassPortNumber(customer.getIc_passport_number())!=null
				&& customerService.findByIcPassPortNumber(customer.getIc_passport_number()).equals(customer.getIc_passport_number())) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("customer", "idexists", "A new customer cannot already have an email or ic_passport"))
					.body(null);
		}
		Customer result = customerService.save(customer);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("customer", customer.getId().toString()))
				.body(result);
	}

	/**
	 * GET /customers -> get all the customers.
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Customer>> getAllCustomers(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Customers");
		Page<Customer> page = customerService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /customers/:id -> get the "id" customer.
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
		log.debug("REST request to get Customer : {}", id);
		Customer customer = customerService.findOne(id);
		return Optional.ofNullable(customer).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /customers/:id -> delete the "id" customer.
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		log.debug("REST request to delete Customer : {}", id);
		customerService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customer", id.toString())).build();
	}

	@RequestMapping(value = "/customers/findAllByIcPassportNumberAndEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Customer>> findAllByIcPassportNumberAndEmail(Pageable pageable,
			@RequestParam(value = "ipnumber", required = false) String ipnumber) throws URISyntaxException {
		log.debug("REST request to get a page of Customers" + ipnumber);
		Page<Customer> page = customerService.findAllByIcPassportNumber(pageable, ipnumber);
		log.debug("REST request to get a page of Customers" + page);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/customers/findAllByIcPassportNumberAndEmail");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
}
