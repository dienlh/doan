package com.hotel.app.service;

import com.hotel.app.domain.Status_service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_service.
 */
public interface Status_serviceService {

    /**
     * Save a status_service.
     * @return the persisted entity
     */
    public Status_service save(Status_service status_service);

    /**
     *  get all the status_services.
     *  @return the list of entities
     */
    public Page<Status_service> findAll(Pageable pageable);

    /**
     *  get the "id" status_service.
     *  @return the entity
     */
    public Status_service findOne(Long id);

    /**
     *  delete the "id" status_service.
     */
    public void delete(Long id);
}
