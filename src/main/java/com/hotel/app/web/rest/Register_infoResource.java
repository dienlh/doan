package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Event;
import com.hotel.app.domain.Register_info;
import com.hotel.app.domain.Room;
import com.hotel.app.domain.Status_register;
import com.hotel.app.service.Register_infoExcelBuilder;
import com.hotel.app.service.Register_infoService;
import com.hotel.app.service.RoomExcelBuilder;
import com.hotel.app.web.rest.util.HeaderUtil;
import com.hotel.app.web.rest.util.PaginationUtil;

import groovy.transform.Undefined;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Register_info.
 */
@RestController
@RequestMapping("/api")
public class Register_infoResource {

	private final Logger log = LoggerFactory.getLogger(Register_infoResource.class);

	@Inject
	private Register_infoService register_infoService;

	/**
	 * POST /register_infos -> Create a new register_info.
	 */
	@RequestMapping(value = "/register_infos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Register_info> createRegister_info(@Valid @RequestBody Register_info register_info)
			throws URISyntaxException {
		log.debug("REST request to save Register_info : {}", register_info);
		if (register_info.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("register_info", "idexists",
					"A new register_info cannot already have an ID")).body(null);
		}
		Status_register status_register = new Status_register();
		status_register.setId(1L);
		register_info.setStatus_register(status_register);
		Register_info result = register_infoService.save(register_info);
		return ResponseEntity.created(new URI("/api/register_infos/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("register_info", result.getId().toString())).body(result);
	}

	/**
	 * PUT /register_infos -> Updates an existing register_info.
	 */
	@RequestMapping(value = "/register_infos", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Register_info> updateRegister_info(@Valid @RequestBody Register_info register_info)
			throws URISyntaxException {
		log.debug("REST request to update Register_info : {}", register_info);
		if (register_info.getId() == null) {
			return createRegister_info(register_info);
		}
		Register_info result = register_infoService.save(register_info);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("register_info", register_info.getId().toString()))
				.body(result);
	}

	/**
	 * GET /register_infos -> get all the register_infos.
	 */
	@RequestMapping(value = "/register_infos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Register_info>> getAllRegister_infos(Pageable pageable,
			@RequestParam(required = false) String filter) throws URISyntaxException {
		if ("reservation-is-null".equals(filter)) {
			log.debug("REST request to get all Register_infos where reservation is null");
			return new ResponseEntity<>(register_infoService.findAllWhereReservationIsNull(), HttpStatus.OK);
		}
		log.debug("REST request to get a page of Register_infos");
		Page<Register_info> page = register_infoService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/register_infos");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /register_infos/:id -> get the "id" register_info.
	 */
	
	@RequestMapping(value = "/register_infos/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Register_info> getRegister_info(@PathVariable Long id) {
		log.debug("REST request to get Register_info : {}", id);
		Register_info register_info = register_infoService.findOne(id);
		return Optional.ofNullable(register_info).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /register_infos/:id -> delete the "id" register_info.
	 */
	@RequestMapping(value = "/register_infos/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteRegister_info(@PathVariable Long id) {
		log.debug("REST request to delete Register_info : {}", id);
		register_infoService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("register_info", id.toString()))
				.build();
	}

	@RequestMapping(value = "/register_infos/findAllByMultiAttr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Register_info>> findAllByMultiAttr(Pageable pageable,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "ipnumber", required = true) String ipnumber,
			@RequestParam(value = "method_payment", required = true) Long method_payment,
			@RequestParam(value = "status_payment", required = true) Long status_payment,
			@RequestParam(value = "method_register", required = true) Long method_register,
			@RequestParam(value = "status_register", required = true) Long status_register,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws URISyntaxException {
		log.debug("REST request to get a page of Register_infos");
		
		if(fromDate==null && toDate==null){
			Page<Register_info> page = register_infoService.findAllByMultiAttr(pageable, code, ipnumber, method_payment,
					status_payment, method_register, status_register);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
					"/api/register_infos/findAllByMultiAttr");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		}else if(fromDate!=null && toDate!=null){
			Page<Register_info> page = register_infoService.findAllByMultiAttr(pageable, code, ipnumber, method_payment,
					status_payment, method_register, status_register, LocalDate.parse(fromDate), LocalDate.parse(toDate));
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
					"/api/register_infos/findAllByMultiAttr");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/register_infos/findAllRegisterChecked", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Register_info>> findAllRegisterChecked() throws URISyntaxException {
		log.debug("REST request to get a page of Register_infos");
		return new ResponseEntity<>(register_infoService.findAllRegisterChecked(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register_infos/exportExcel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ModelAndView exportExcel(
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "ipnumber", required = true) String ipnumber,
			@RequestParam(value = "method_payment", required = true) Long method_payment,
			@RequestParam(value = "status_payment", required = true) Long status_payment,
			@RequestParam(value = "method_register", required = true) Long method_register,
			@RequestParam(value = "status_register", required = true) Long status_register,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate)
			throws URISyntaxException {
		log.debug("Start exportExcel ");
		List<Register_info> register_infos = new ArrayList<>();
		if((fromDate==null && toDate==null) || (fromDate.equals("undefined") && toDate.equals("undefined"))){
			register_infos = register_infoService.findAllByMultiAttr(code, ipnumber, method_payment,
					status_payment, method_register, status_register);
		}else if(fromDate!=null && toDate!=null){
			register_infos = register_infoService.findAllByMultiAttr(code, ipnumber, method_payment,
					status_payment, method_register, status_register, LocalDate.parse(fromDate), LocalDate.parse(toDate));
		}else{
			return null;
		}
		return new ModelAndView(new Register_infoExcelBuilder(), "lists", register_infos);
	}

}
