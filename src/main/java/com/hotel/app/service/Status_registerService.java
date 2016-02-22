package com.hotel.app.service;

import com.hotel.app.domain.Status_register;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_register.
 */
public interface Status_registerService {

    /**
     * Save a status_register.
     * @return the persisted entity
     */
    public Status_register save(Status_register status_register);

    /**
     *  get all the status_registers.
     *  @return the list of entities
     */
    public Page<Status_register> findAll(Pageable pageable);

    /**
     *  get the "id" status_register.
     *  @return the entity
     */
    public Status_register findOne(Long id);

    /**
     *  delete the "id" status_register.
     */
    public void delete(Long id);
}
