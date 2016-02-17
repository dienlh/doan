package com.hotel.app.service;

import com.hotel.app.domain.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Bank.
 */
public interface BankService {

    /**
     * Save a bank.
     * @return the persisted entity
     */
    public Bank save(Bank bank);

    /**
     *  get all the banks.
     *  @return the list of entities
     */
    public Page<Bank> findAll(Pageable pageable);

    /**
     *  get the "id" bank.
     *  @return the entity
     */
    public Bank findOne(Long id);

    /**
     *  delete the "id" bank.
     */
    public void delete(Long id);
}
