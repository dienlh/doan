package com.hotel.app.service;

import com.hotel.app.domain.Religion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Religion.
 */
public interface ReligionService {

    /**
     * Save a religion.
     * @return the persisted entity
     */
    public Religion save(Religion religion);

    /**
     *  get all the religions.
     *  @return the list of entities
     */
    public Page<Religion> findAll(Pageable pageable);

    /**
     *  get the "id" religion.
     *  @return the entity
     */
    public Religion findOne(Long id);

    /**
     *  delete the "id" religion.
     */
    public void delete(Long id);
}
