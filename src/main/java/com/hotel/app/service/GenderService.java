package com.hotel.app.service;

import com.hotel.app.domain.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Gender.
 */
public interface GenderService {

    /**
     * Save a gender.
     * @return the persisted entity
     */
    public Gender save(Gender gender);

    /**
     *  get all the genders.
     *  @return the list of entities
     */
    public Page<Gender> findAll(Pageable pageable);

    /**
     *  get the "id" gender.
     *  @return the entity
     */
    public Gender findOne(Long id);

    /**
     *  delete the "id" gender.
     */
    public void delete(Long id);
}
