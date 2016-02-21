package com.hotel.app.service.impl;

import com.hotel.app.service.ImageService;
import com.hotel.app.domain.Image;
import com.hotel.app.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService{

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
    
    @Inject
    private ImageRepository imageRepository;
    
    /**
     * Save a image.
     * @return the persisted entity
     */
    public Image save(Image image) {
        log.debug("Request to save Image : {}", image);
        Image result = imageRepository.save(image);
        return result;
    }

    /**
     *  get all the images.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Image> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        Page<Image> result = imageRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one image by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Image findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        Image image = imageRepository.findOne(id);
        return image;
    }

    /**
     *  delete the  image by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.delete(id);
    }
}
