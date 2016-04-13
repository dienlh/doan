package com.hotel.app.service;

import com.hotel.app.domain.Education_level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Education_level.
 */
public interface Education_levelService {

    /**
     * Save a education_level.
     * @return the persisted entity
     */
    public Education_level save(Education_level education_level);

    /**
     *  get all the education_levels.
     *  @return the list of entities
     */
    public Page<Education_level> findAll(Pageable pageable);

    /**
     *  get the "id" education_level.
     *  @return the entity
     */
    public Education_level findOne(Long id);

    /**
     *  delete the "id" education_level.
     */
    public void delete(Long id);
}
