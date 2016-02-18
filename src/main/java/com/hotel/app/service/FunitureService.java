package com.hotel.app.service;

import com.hotel.app.domain.Funiture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Funiture.
 */
public interface FunitureService {

    /**
     * Save a funiture.
     * @return the persisted entity
     */
    public Funiture save(Funiture funiture);

    /**
     *  get all the funitures.
     *  @return the list of entities
     */
    public Page<Funiture> findAll(Pageable pageable);

    /**
     *  get the "id" funiture.
     *  @return the entity
     */
    public Funiture findOne(Long id);

    /**
     *  delete the "id" funiture.
     */
    public void delete(Long id);
}
