package com.hotel.app.service;

import com.hotel.app.domain.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Position.
 */
public interface PositionService {

    /**
     * Save a position.
     * @return the persisted entity
     */
    public Position save(Position position);

    /**
     *  get all the positions.
     *  @return the list of entities
     */
    public Page<Position> findAll(Pageable pageable);

    /**
     *  get the "id" position.
     *  @return the entity
     */
    public Position findOne(Long id);

    /**
     *  delete the "id" position.
     */
    public void delete(Long id);
}
