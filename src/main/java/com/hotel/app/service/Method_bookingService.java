package com.hotel.app.service;

import com.hotel.app.domain.Method_booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Method_booking.
 */
public interface Method_bookingService {

    /**
     * Save a method_booking.
     * @return the persisted entity
     */
    public Method_booking save(Method_booking method_booking);

    /**
     *  get all the method_bookings.
     *  @return the list of entities
     */
    public Page<Method_booking> findAll(Pageable pageable);

    /**
     *  get the "id" method_booking.
     *  @return the entity
     */
    public Method_booking findOne(Long id);

    /**
     *  delete the "id" method_booking.
     */
    public void delete(Long id);
}
