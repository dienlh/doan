package com.hotel.app.service;

import com.hotel.app.domain.Status_event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_event.
 */
public interface Status_eventService {

    /**
     * Save a status_event.
     * @return the persisted entity
     */
    public Status_event save(Status_event status_event);

    /**
     *  get all the status_events.
     *  @return the list of entities
     */
    public Page<Status_event> findAll(Pageable pageable);

    /**
     *  get the "id" status_event.
     *  @return the entity
     */
    public Status_event findOne(Long id);

    /**
     *  delete the "id" status_event.
     */
    public void delete(Long id);
}
