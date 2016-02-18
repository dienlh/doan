package com.hotel.app.service;

import com.hotel.app.domain.Status_pe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_pe.
 */
public interface Status_peService {

    /**
     * Save a status_pe.
     * @return the persisted entity
     */
    public Status_pe save(Status_pe status_pe);

    /**
     *  get all the status_pes.
     *  @return the list of entities
     */
    public Page<Status_pe> findAll(Pageable pageable);

    /**
     *  get the "id" status_pe.
     *  @return the entity
     */
    public Status_pe findOne(Long id);

    /**
     *  delete the "id" status_pe.
     */
    public void delete(Long id);
}
