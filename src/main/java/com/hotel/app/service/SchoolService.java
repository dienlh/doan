package com.hotel.app.service;

import com.hotel.app.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing School.
 */
public interface SchoolService {

    /**
     * Save a school.
     * @return the persisted entity
     */
    public School save(School school);

    /**
     *  get all the schools.
     *  @return the list of entities
     */
    public Page<School> findAll(Pageable pageable);

    /**
     *  get the "id" school.
     *  @return the entity
     */
    public School findOne(Long id);

    /**
     *  delete the "id" school.
     */
    public void delete(Long id);
}
