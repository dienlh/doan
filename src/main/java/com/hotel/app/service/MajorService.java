package com.hotel.app.service;

import com.hotel.app.domain.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Major.
 */
public interface MajorService {

    /**
     * Save a major.
     * @return the persisted entity
     */
    public Major save(Major major);

    /**
     *  get all the majors.
     *  @return the list of entities
     */
    public Page<Major> findAll(Pageable pageable);

    /**
     *  get the "id" major.
     *  @return the entity
     */
    public Major findOne(Long id);

    /**
     *  delete the "id" major.
     */
    public void delete(Long id);
}
