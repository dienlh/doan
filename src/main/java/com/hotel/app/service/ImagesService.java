package com.hotel.app.service;

import com.hotel.app.domain.Images;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Images.
 */
public interface ImagesService {

    /**
     * Save a images.
     * @return the persisted entity
     */
    public Images save(Images images);

    /**
     *  get all the imagess.
     *  @return the list of entities
     */
    public Page<Images> findAll(Pageable pageable);

    /**
     *  get the "id" images.
     *  @return the entity
     */
    public Images findOne(Long id);

    /**
     *  delete the "id" images.
     */
    public void delete(Long id);
}
