package com.hotel.app.service.impl;

import com.hotel.app.service.RoomService;
import com.hotel.app.domain.Room;
import com.hotel.app.repository.RoomRepository;
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
 * Service Implementation for managing Room.
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService{

    private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);
    
    @Inject
    private RoomRepository roomRepository;
    
    /**
     * Save a room.
     * @return the persisted entity
     */
    public Room save(Room room) {
        log.debug("Request to save Room : {}", room);
        Room result = roomRepository.save(room);
        return result;
    }

    /**
     *  get all the rooms.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Room> findAll(Pageable pageable) {
        log.debug("Request to get all Rooms");
        Page<Room> result = roomRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one room by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Room findOne(Long id) {
        log.debug("Request to get Room : {}", id);
        Room room = roomRepository.findOne(id);
        return room;
    }

    /**
     *  delete the  room by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);
        roomRepository.delete(id);
    }
}
