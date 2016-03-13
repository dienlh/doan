package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.hotel.app.domain.Event;
import com.hotel.app.service.EventService;
import com.hotel.app.web.rest.util.HeaderUtil;
import com.hotel.app.web.rest.util.PaginationUtil;

import org.mockito.exceptions.Reporter;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

	private final Logger log = LoggerFactory.getLogger(EventResource.class);

	@Inject
	private EventService eventService;

	/**
	 * POST /events -> Create a new event.
	 */
	@RequestMapping(value = "/events", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) throws URISyntaxException {
		log.debug("REST request to save Event : {}", event);
		if (event.getId() != null) {
			return ResponseEntity.badRequest()
					.headers(
							HeaderUtil.createFailureAlert("event", "idexists", "A new event cannot already have an ID"))
					.body(null);
		}
		Event result = eventService.save(event);
		return ResponseEntity.created(new URI("/api/events/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("event", result.getId().toString())).body(result);
	}

	/**
	 * PUT /events -> Updates an existing event.
	 */
	@RequestMapping(value = "/events", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Event> updateEvent(@Valid @RequestBody Event event) throws URISyntaxException {
		log.debug("REST request to update Event : {}", event);
		if (event.getId() == null) {
			return createEvent(event);
		}
		Event result = eventService.save(event);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("event", event.getId().toString()))
				.body(result);
	}

	/**
	 * GET /events -> get all the events.
	 */
	@RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Event>> getAllEvents(Pageable pageable) throws URISyntaxException {
		log.debug("REST request to get a page of Events");
		Page<Event> page = eventService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /events/:id -> get the "id" event.
	 */
	@RequestMapping(value = "/events/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Event> getEvent(@PathVariable Long id) {
		log.debug("REST request to get Event : {}", id);
		Event event = eventService.findOne(id);
		return Optional.ofNullable(event).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /events/:id -> delete the "id" event.
	 */
	@RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		log.debug("REST request to delete Event : {}", id);
		eventService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("event", id.toString())).build();
	}

	@RequestMapping(value = "/events/findAllByRangerDateAndStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Event>> findAllByRangerDateAndStatus(Pageable pageable,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "statusId", required = true) Long statusId) throws URISyntaxException {
		log.debug("REST request to get a page of Events" + fromDate+"||"+toDate+"||"+statusId);
		if(fromDate==null && toDate==null){
			Page<Event> page = eventService.findAllByStatusId(pageable, statusId);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events/findAllByRangerDateAndStatus");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		}else if(fromDate!=null && toDate!=null){
			Page<Event> page = eventService.findAllByRangerDateAndStatus(pageable, LocalDate.parse(fromDate), LocalDate.parse(toDate), statusId);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events/findAllByRangerDateAndStatus");
			return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
