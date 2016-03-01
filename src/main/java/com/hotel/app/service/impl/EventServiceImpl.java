package com.hotel.app.service.impl;

import com.hotel.app.service.EventService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Event;
import com.hotel.app.domain.User;
import com.hotel.app.repository.EventRepository;
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

/**
 * Service Implementation for managing Event.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService{

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    
    @Inject
    private EventRepository eventRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a event.
     * @return the persisted entity
     */
    public Event save(Event event) {
        log.debug("Request to save Event : {}", event);
        if (event.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			event.setCreate_by(user);
			log.info("Preshow user" + user);
		} else {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			// user.setLogin(optional.get().getLogin());
			event.setLast_modified_by(user);
			event.setLast_modified_date(ZonedDateTime.now());
		}
        Event result = eventRepository.save(event);
        return result;
    }

    /**
     *  get all the events.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Event> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        Page<Event> result = eventRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one event by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Event findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        Event event = eventRepository.findOne(id);
        return event;
    }

    /**
     *  delete the  event by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.delete(id);
    }
}
