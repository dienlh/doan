package com.hotel.app.service;

import com.hotel.app.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Reservation.
 */
public interface ReservationService {

    /**
     * Save a reservation.
     * @return the persisted entity
     */
    public Reservation save(Reservation reservation);

    /**
     *  get all the reservations.
     *  @return the list of entities
     */
    public Page<Reservation> findAll(Pageable pageable);
    /**
     *  get all the reservations where Bill is null.
     *  @return the list of entities
     */
    public List<Reservation> findAllWhereBillIsNull();

    /**
     *  get the "id" reservation.
     *  @return the entity
     */
    public Reservation findOne(Long id);

    /**
     *  delete the "id" reservation.
     */
    public void delete(Long id);
    
    public List<Reservation> findReservationNotCheckout();
}
