package com.hotel.app.service;

import com.hotel.app.domain.Came_component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Came_component.
 */
public interface Came_componentService {

    /**
     * Save a came_component.
     * @return the persisted entity
     */
    public Came_component save(Came_component came_component);

    /**
     *  get all the came_components.
     *  @return the list of entities
     */
    public Page<Came_component> findAll(Pageable pageable);

    /**
     *  get the "id" came_component.
     *  @return the entity
     */
    public Came_component findOne(Long id);

    /**
     *  delete the "id" came_component.
     */
    public void delete(Long id);
}
