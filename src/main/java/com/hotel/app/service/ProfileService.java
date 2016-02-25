package com.hotel.app.service;

import com.hotel.app.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Profile.
 */
public interface ProfileService {

    /**
     * Save a profile.
     * @return the persisted entity
     */
    public Profile save(Profile profile);

    /**
     *  get all the profiles.
     *  @return the list of entities
     */
    public Page<Profile> findAll(Pageable pageable);

    /**
     *  get the "id" profile.
     *  @return the entity
     */
    public Profile findOne(Long id);

    /**
     *  delete the "id" profile.
     */
    public void delete(Long id);
}
