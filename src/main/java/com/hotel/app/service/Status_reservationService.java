package com.hotel.app.service;

import com.hotel.app.domain.Status_reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_reservation.
 */
public interface Status_reservationService {

    /**
     * Save a status_reservation.
     * @return the persisted entity
     */
    public Status_reservation save(Status_reservation status_reservation);

    /**
     *  get all the status_reservations.
     *  @return the list of entities
     */
    public Page<Status_reservation> findAll(Pageable pageable);

    /**
     *  get the "id" status_reservation.
     *  @return the entity
     */
    public Status_reservation findOne(Long id);

    /**
     *  delete the "id" status_reservation.
     */
    public void delete(Long id);
}
