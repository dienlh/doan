package com.hotel.app.service.impl;

import com.hotel.app.service.ImagesService;
import com.hotel.app.domain.Images;
import com.hotel.app.repository.ImagesRepository;
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
 * Service Implementation for managing Images.
 */
@Service
@Transactional
public class ImagesServiceImpl implements ImagesService{

    private final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);
    
    @Inject
    private ImagesRepository imagesRepository;
    
    /**
     * Save a images.
     * @return the persisted entity
     */
    public Images save(Images images) {
        log.debug("Request to save Images : {}", images);
        Images result = imagesRepository.save(images);
        return result;
    }

    /**
     *  get all the imagess.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Images> findAll(Pageable pageable) {
        log.debug("Request to get all Imagess");
        Page<Images> result = imagesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one images by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Images findOne(Long id) {
        log.debug("Request to get Images : {}", id);
        Images images = imagesRepository.findOne(id);
        return images;
    }

    /**
     *  delete the  images by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Images : {}", id);
        imagesRepository.delete(id);
    }
}
