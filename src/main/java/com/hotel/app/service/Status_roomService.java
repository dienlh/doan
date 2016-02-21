package com.hotel.app.service;

import com.hotel.app.domain.Status_room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_room.
 */
public interface Status_roomService {

    /**
     * Save a status_room.
     * @return the persisted entity
     */
    public Status_room save(Status_room status_room);

    /**
     *  get all the status_rooms.
     *  @return the list of entities
     */
    public Page<Status_room> findAll(Pageable pageable);

    /**
     *  get the "id" status_room.
     *  @return the entity
     */
    public Status_room findOne(Long id);

    /**
     *  delete the "id" status_room.
     */
    public void delete(Long id);
}
