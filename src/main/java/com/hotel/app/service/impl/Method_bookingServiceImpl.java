package com.hotel.app.service.impl;

import com.hotel.app.service.Method_bookingService;
import com.hotel.app.domain.Method_booking;
import com.hotel.app.repository.Method_bookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Method_booking.
 */
@Service
@Transactional
public class Method_bookingServiceImpl implements Method_bookingService{

    private final Logger log = LoggerFactory.getLogger(Method_bookingServiceImpl.class);
    
    @Inject
    private Method_bookingRepository method_bookingRepository;
    
    /**
     * Save a method_booking.
     * @return the persisted entity
     */
    public Method_booking save(Method_booking method_booking) {
        log.debug("Request to save Method_booking : {}", method_booking);
        Method_booking result = method_bookingRepository.save(method_booking);
        return result;
    }

    /**
     *  get all the method_bookings.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Method_booking> findAll(Pageable pageable) {
        log.debug("Request to get all Method_bookings");
        Page<Method_booking> result = method_bookingRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one method_booking by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Method_booking findOne(Long id) {
        log.debug("Request to get Method_booking : {}", id);
        Method_booking method_booking = method_bookingRepository.findOne(id);
        return method_booking;
    }

    /**
     *  delete the  method_booking by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Method_booking : {}", id);
        method_bookingRepository.delete(id);
    }
}
