package com.hotel.app.service;

import com.hotel.app.domain.Amenity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Amenity.
 */
public interface AmenityService {

    /**
     * Save a amenity.
     * @return the persisted entity
     */
    public Amenity save(Amenity amenity);

    /**
     *  get all the amenitys.
     *  @return the list of entities
     */
    public Page<Amenity> findAll(Pageable pageable);

    /**
     *  get the "id" amenity.
     *  @return the entity
     */
    public Amenity findOne(Long id);

    /**
     *  delete the "id" amenity.
     */
    public void delete(Long id);
}
