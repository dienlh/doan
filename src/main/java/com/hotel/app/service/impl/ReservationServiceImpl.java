package com.hotel.app.service.impl;

import com.hotel.app.service.ReservationService;
import com.hotel.app.domain.Reservation;
import com.hotel.app.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Reservation.
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

    private final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);
    
    @Inject
    private ReservationRepository reservationRepository;
    
    /**
     * Save a reservation.
     * @return the persisted entity
     */
    public Reservation save(Reservation reservation) {
        log.debug("Request to save Reservation : {}", reservation);
        Reservation result = reservationRepository.save(reservation);
        return result;
    }

    /**
     *  get all the reservations.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Reservation> findAll(Pageable pageable) {
        log.debug("Request to get all Reservations");
        Page<Reservation> result = reservationRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the reservations where Bill is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Reservation> findAllWhereBillIsNull() {
        log.debug("Request to get all reservations where Bill is null");
        return StreamSupport
            .stream(reservationRepository.findAll().spliterator(), false)
            .filter(reservation -> reservation.getBill() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one reservation by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Reservation findOne(Long id) {
        log.debug("Request to get Reservation : {}", id);
        Reservation reservation = reservationRepository.findOneWithEagerRelationships(id);
        return reservation;
    }

    /**
     *  delete the  reservation by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reservation : {}", id);
        reservationRepository.delete(id);
    }
}
