package com.hotel.app.service;

import com.hotel.app.domain.Status_bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_bill.
 */
public interface Status_billService {

    /**
     * Save a status_bill.
     * @return the persisted entity
     */
    public Status_bill save(Status_bill status_bill);

    /**
     *  get all the status_bills.
     *  @return the list of entities
     */
    public Page<Status_bill> findAll(Pageable pageable);

    /**
     *  get the "id" status_bill.
     *  @return the entity
     */
    public Status_bill findOne(Long id);

    /**
     *  delete the "id" status_bill.
     */
    public void delete(Long id);
}
