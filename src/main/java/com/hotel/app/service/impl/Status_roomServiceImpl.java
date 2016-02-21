package com.hotel.app.service.impl;

import com.hotel.app.service.Status_roomService;
import com.hotel.app.domain.Status_room;
import com.hotel.app.repository.Status_roomRepository;
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
 * Service Implementation for managing Status_room.
 */
@Service
@Transactional
public class Status_roomServiceImpl implements Status_roomService{

    private final Logger log = LoggerFactory.getLogger(Status_roomServiceImpl.class);
    
    @Inject
    private Status_roomRepository status_roomRepository;
    
    /**
     * Save a status_room.
     * @return the persisted entity
     */
    public Status_room save(Status_room status_room) {
        log.debug("Request to save Status_room : {}", status_room);
        Status_room result = status_roomRepository.save(status_room);
        return result;
    }

    /**
     *  get all the status_rooms.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_room> findAll(Pageable pageable) {
        log.debug("Request to get all Status_rooms");
        Page<Status_room> result = status_roomRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_room by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_room findOne(Long id) {
        log.debug("Request to get Status_room : {}", id);
        Status_room status_room = status_roomRepository.findOne(id);
        return status_room;
    }

    /**
     *  delete the  status_room by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_room : {}", id);
        status_roomRepository.delete(id);
    }
}
