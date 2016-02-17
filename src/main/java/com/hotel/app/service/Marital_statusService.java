package com.hotel.app.service;

import com.hotel.app.domain.Marital_status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Marital_status.
 */
public interface Marital_statusService {

    /**
     * Save a marital_status.
     * @return the persisted entity
     */
    public Marital_status save(Marital_status marital_status);

    /**
     *  get all the marital_statuss.
     *  @return the list of entities
     */
    public Page<Marital_status> findAll(Pageable pageable);

    /**
     *  get the "id" marital_status.
     *  @return the entity
     */
    public Marital_status findOne(Long id);

    /**
     *  delete the "id" marital_status.
     */
    public void delete(Long id);
}
