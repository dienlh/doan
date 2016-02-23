package com.hotel.app.service;

import com.hotel.app.domain.Type_room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Type_room.
 */
public interface Type_roomService {

    /**
     * Save a type_room.
     * @return the persisted entity
     */
    public Type_room save(Type_room type_room);

    /**
     *  get all the type_rooms.
     *  @return the list of entities
     */
    public Page<Type_room> findAll(Pageable pageable);

    /**
     *  get the "id" type_room.
     *  @return the entity
     */
    public Type_room findOne(Long id);

    /**
     *  delete the "id" type_room.
     */
    public void delete(Long id);
}
