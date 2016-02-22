package com.hotel.app.service;

import com.hotel.app.domain.Method_register;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Method_register.
 */
public interface Method_registerService {

    /**
     * Save a method_register.
     * @return the persisted entity
     */
    public Method_register save(Method_register method_register);

    /**
     *  get all the method_registers.
     *  @return the list of entities
     */
    public Page<Method_register> findAll(Pageable pageable);

    /**
     *  get the "id" method_register.
     *  @return the entity
     */
    public Method_register findOne(Long id);

    /**
     *  delete the "id" method_register.
     */
    public void delete(Long id);
}
