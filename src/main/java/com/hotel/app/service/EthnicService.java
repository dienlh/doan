package com.hotel.app.service;

import com.hotel.app.domain.Ethnic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Ethnic.
 */
public interface EthnicService {

    /**
     * Save a ethnic.
     * @return the persisted entity
     */
    public Ethnic save(Ethnic ethnic);

    /**
     *  get all the ethnics.
     *  @return the list of entities
     */
    public Page<Ethnic> findAll(Pageable pageable);

    /**
     *  get the "id" ethnic.
     *  @return the entity
     */
    public Ethnic findOne(Long id);

    /**
     *  delete the "id" ethnic.
     */
    public void delete(Long id);
}
