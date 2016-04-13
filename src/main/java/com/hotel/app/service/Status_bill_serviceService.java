package com.hotel.app.service;

import com.hotel.app.domain.Status_bill_service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_bill_service.
 */
public interface Status_bill_serviceService {

    /**
     * Save a status_bill_service.
     * @return the persisted entity
     */
    public Status_bill_service save(Status_bill_service status_bill_service);

    /**
     *  get all the status_bill_services.
     *  @return the list of entities
     */
    public Page<Status_bill_service> findAll(Pageable pageable);

    /**
     *  get the "id" status_bill_service.
     *  @return the entity
     */
    public Status_bill_service findOne(Long id);

    /**
     *  delete the "id" status_bill_service.
     */
    public void delete(Long id);
}
