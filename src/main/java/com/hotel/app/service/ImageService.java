package com.hotel.app.service;

import com.hotel.app.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Image.
 */
public interface ImageService {

    /**
     * Save a image.
     * @return the persisted entity
     */
    public Image save(Image image);

    /**
     *  get all the images.
     *  @return the list of entities
     */
    public Page<Image> findAll(Pageable pageable);

    /**
     *  get the "id" image.
     *  @return the entity
     */
    public Image findOne(Long id);

    /**
     *  delete the "id" image.
     */
    public void delete(Long id);
}
