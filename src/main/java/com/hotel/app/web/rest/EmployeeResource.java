package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Employee;
import com.hotel.app.service.EmployeeService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

	private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

	@Inject
	private EmployeeService employeeService;

	/**
	 * POST /employees -> Create a new employee.
	 */
	@RequestMapping(value = "/employees", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
		log.debug("REST request to save Employee : {}", employee);
		if (employee.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("employee", "idexists", "A new employee cannot already have an ID"))
					.body(null);
		}
		Employee result = employeeService.save(employee);
		return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("employee", result.getId().toString())).body(result);
	}

	/**
	 * PUT /employees -> Updates an existing employee.
	 */
	@RequestMapping(value = "/employees", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
		log.debug("REST request to update Employee : {}", employee);
		if (employee.getId() == null) {
			return createEmployee(employee);
		}
		Employee result = employeeService.save(employee);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("employee", employee.getId().toString()))
				.body(result);
	}

	/**
	 * GET /employees -> get all the employees.
	 */
	@RequestMapping(value = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Employee>> getAllEmployees(Pageable pageable,
			@RequestParam(required = false) String filter) throws URISyntaxException {
		if ("profile-is-null".equals(filter)) {
			log.debug("REST request to get all Employees where profile is null");
			return new ResponseEntity<>(employeeService.findAllWhereProfileIsNull(), HttpStatus.OK);
		}
		log.debug("REST request to get a page of Employees");
		Page<Employee> page = employeeService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employees");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /employees -> get the employees with Ic and Email.
	 */
	// @RequestMapping(value =
	// "/employees/findAllByEmailContainingOrIc_numberContaining", method =
	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @Timed
	// public ResponseEntity<List<Employee>>
	// getEmployeesByIc_numberAndEmail(Pageable pageable,
	// @RequestParam(value = "email") String email ,
	// @RequestParam(required=false,value="icnumber") String ic_number) throws
	// URISyntaxException {
	// log.debug("REST request to get a page of Employees"+email);
	// Page<Employee> page = employeeService.findAllByIc_number(pageable,
	// ic_number);
	// HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
	// "/api/employees/findAllByEmailContainingOrIc_numberContaining");
	// return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	// }

	/**
	 * GET /employees -> get the employees with Ic and Email.
	 */
	@RequestMapping(value = "/employees/findAllByIc_number", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Employee>> findAllByIc_numberAndEmail(Pageable pageable,
			@RequestParam(required = false, value = "icnumber") String ic_number,
			@RequestParam(required = false, value = "email") String email) throws URISyntaxException {
		log.debug("REST request to get a page of Employees" + ic_number);
		Page<Employee> page = employeeService.findAllByIc_numberAndEmail(pageable, ic_number,email);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employees/findAllByIc_number");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /employees/:id -> get the "id" employee.
	 */
	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
		log.debug("REST request to get Employee : {}", id);
		Employee employee = employeeService.findOne(id);
		return Optional.ofNullable(employee).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /employees/:id -> delete the "id" employee.
	 */
	@RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		log.debug("REST request to delete Employee : {}", id);
		employeeService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employee", id.toString())).build();
	}
}
