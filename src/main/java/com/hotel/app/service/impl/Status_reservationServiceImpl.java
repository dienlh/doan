package com.hotel.app.service.impl;

import com.hotel.app.service.Status_reservationService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Status_reservation;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Status_reservationRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

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
 * Service Implementation for managing Status_reservation.
 */
@Service
@Transactional
public class Status_reservationServiceImpl implements Status_reservationService{

    private final Logger log = LoggerFactory.getLogger(Status_reservationServiceImpl.class);
    
    @Inject
    private Status_reservationRepository status_reservationRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a status_reservation.
     * @return the persisted entity
     */
    public Status_reservation save(Status_reservation status_reservation) {
        log.debug("Request to save Status_reservation : {}", status_reservation);
        if(status_reservation.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            status_reservation.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Status_reservation result = status_reservationRepository.save(status_reservation);
        return result;
    }

    /**
     *  get all the status_reservations.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_reservation> findAll(Pageable pageable) {
        log.debug("Request to get all Status_reservations");
        Page<Status_reservation> result = status_reservationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_reservation by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_reservation findOne(Long id) {
        log.debug("Request to get Status_reservation : {}", id);
        Status_reservation status_reservation = status_reservationRepository.findOne(id);
        return status_reservation;
    }

    /**
     *  delete the  status_reservation by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_reservation : {}", id);
        status_reservationRepository.delete(id);
    }
}
