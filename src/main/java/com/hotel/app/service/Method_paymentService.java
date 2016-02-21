package com.hotel.app.service;

import com.hotel.app.domain.Method_payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Method_payment.
 */
public interface Method_paymentService {

    /**
     * Save a method_payment.
     * @return the persisted entity
     */
    public Method_payment save(Method_payment method_payment);

    /**
     *  get all the method_payments.
     *  @return the list of entities
     */
    public Page<Method_payment> findAll(Pageable pageable);

    /**
     *  get the "id" method_payment.
     *  @return the entity
     */
    public Method_payment findOne(Long id);

    /**
     *  delete the "id" method_payment.
     */
    public void delete(Long id);
}
