package com.hotel.app.service.impl;

import com.hotel.app.service.ReservationService;
import com.hotel.app.service.RoomService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Reservation;
import com.hotel.app.domain.Status_reservation;
import com.hotel.app.domain.Status_room;
import com.hotel.app.domain.User;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
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
    
    @Inject
    private RoomService roomService;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a reservation.
     * @return the persisted entity
     */
    public Reservation save(Reservation reservation) {
        log.debug("Request to save Reservation : {}", reservation);
        if(reservation.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
        	Status_reservation status_reservation = new Status_reservation();
        	status_reservation.setId(1L);
        	reservation.setStatus_reservation(status_reservation);
        	
            User user=new User();
            user.setId(optional.get().getId());
//            user.setLogin(optional.get().getLogin());
            reservation.setCreate_by(user);
            reservation.setTime_checkin(ZonedDateTime.now());
            
            Status_room status_room = new Status_room();
            status_room.setId(1L);
            reservation.getRegister_info().getRoom().setStatus_room(status_room);
            log.info("Preshow user"+ user);
        }else{
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            User user=new User();
            user.setId(optional.get().getId());
//            user.setLogin(optional.get().getLogin());
            reservation.setLast_modified_by(user);
            reservation.setLast_modified_date(ZonedDateTime.now());
            
            if (reservation.getTime_checkout() != null) {
    			Reservation reservation2 = reservationRepository.findOne(reservation.getId());
    			if (reservation2.getTime_checkout() == null) {
    				reservation.setTime_checkout(ZonedDateTime.now());
    				Status_reservation status_reservation = new Status_reservation();
    				status_reservation.setId(2L);
    				reservation.setStatus_reservation(status_reservation);
    			
    				Status_room status_room = new Status_room();
    				status_room.setId(4L);
    				reservation.getRegister_info().getRoom().setStatus_room(status_room);
    			}
    		}
        }
        Reservation result = reservationRepository.save(reservation);
        roomService.save(reservation.getRegister_info().getRoom());
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
    
    @Override
    public List<Reservation> findReservationNotCheckout() {
    	return reservationRepository.findReservationNotCheckout();
    }
}
