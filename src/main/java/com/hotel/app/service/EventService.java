package com.hotel.app.service;

import com.hotel.app.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Interface for managing Event.
 */
public interface EventService {

    /**
     * Save a event.
     * @return the persisted entity
     */
    public Event save(Event event);

    /**
     *  get all the events.
     *  @return the list of entities
     */
    public Page<Event> findAll(Pageable pageable);

    /**
     *  get the "id" event.
     *  @return the entity
     */
    public Event findOne(Long id);

    /**
     *  delete the "id" event.
     */
    public void delete(Long id);
    
    public Page<Event> findAllByRangerDateAndStatus(Pageable pageable, LocalDate fromDate, LocalDate toDate, Long statusId);
    
    public Page<Event> findAllByStatusId(Pageable pageable,Long statusId);
}
