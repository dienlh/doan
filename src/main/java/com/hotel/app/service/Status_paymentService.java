package com.hotel.app.service;

import com.hotel.app.domain.Status_payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_payment.
 */
public interface Status_paymentService {

    /**
     * Save a status_payment.
     * @return the persisted entity
     */
    public Status_payment save(Status_payment status_payment);

    /**
     *  get all the status_payments.
     *  @return the list of entities
     */
    public Page<Status_payment> findAll(Pageable pageable);

    /**
     *  get the "id" status_payment.
     *  @return the entity
     */
    public Status_payment findOne(Long id);

    /**
     *  delete the "id" status_payment.
     */
    public void delete(Long id);
}
