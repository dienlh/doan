package com.hotel.app.service.impl;

import com.hotel.app.service.Status_eventService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Status_event;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Status_eventRepository;
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
 * Service Implementation for managing Status_event.
 */
@Service
@Transactional
public class Status_eventServiceImpl implements Status_eventService{

    private final Logger log = LoggerFactory.getLogger(Status_eventServiceImpl.class);
    
    @Inject
    private Status_eventRepository status_eventRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a status_event.
     * @return the persisted entity
     */
    public Status_event save(Status_event status_event) {
        log.debug("Request to save Status_event : {}", status_event);
        if(status_event.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            status_event.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Status_event result = status_eventRepository.save(status_event);
        return result;
    }

    /**
     *  get all the status_events.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_event> findAll(Pageable pageable) {
        log.debug("Request to get all Status_events");
        Page<Status_event> result = status_eventRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_event by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_event findOne(Long id) {
        log.debug("Request to get Status_event : {}", id);
        Status_event status_event = status_eventRepository.findOne(id);
        return status_event;
    }

    /**
     *  delete the  status_event by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_event : {}", id);
        status_eventRepository.delete(id);
    }
}
