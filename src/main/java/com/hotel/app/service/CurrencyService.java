package com.hotel.app.service;

import com.hotel.app.domain.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Currency.
 */
public interface CurrencyService {

    /**
     * Save a currency.
     * @return the persisted entity
     */
    public Currency save(Currency currency);

    /**
     *  get all the currencys.
     *  @return the list of entities
     */
    public Page<Currency> findAll(Pageable pageable);

    /**
     *  get the "id" currency.
     *  @return the entity
     */
    public Currency findOne(Long id);

    /**
     *  delete the "id" currency.
     */
    public void delete(Long id);
}
