package com.hotel.app.service;

import com.hotel.app.domain.Status_profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_profile.
 */
public interface Status_profileService {

    /**
     * Save a status_profile.
     * @return the persisted entity
     */
    public Status_profile save(Status_profile status_profile);

    /**
     *  get all the status_profiles.
     *  @return the list of entities
     */
    public Page<Status_profile> findAll(Pageable pageable);

    /**
     *  get the "id" status_profile.
     *  @return the entity
     */
    public Status_profile findOne(Long id);

    /**
     *  delete the "id" status_profile.
     */
    public void delete(Long id);
}
