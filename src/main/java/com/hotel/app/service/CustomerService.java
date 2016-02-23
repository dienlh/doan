package com.hotel.app.service;

import com.hotel.app.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Customer.
 */
public interface CustomerService {

    /**
     * Save a customer.
     * @return the persisted entity
     */
    public Customer save(Customer customer);

    /**
     *  get all the customers.
     *  @return the list of entities
     */
    public Page<Customer> findAll(Pageable pageable);

    /**
     *  get the "id" customer.
     *  @return the entity
     */
    public Customer findOne(Long id);

    /**
     *  delete the "id" customer.
     */
    public void delete(Long id);
}
