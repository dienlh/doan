package com.hotel.app.service;

import com.hotel.app.domain.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Services.
 */
public interface ServicesService {

    /**
     * Save a services.
     * @return the persisted entity
     */
    public Services save(Services services);

    /**
     *  get all the servicess.
     *  @return the list of entities
     */
    public Page<Services> findAll(Pageable pageable);

    /**
     *  get the "id" services.
     *  @return the entity
     */
    public Services findOne(Long id);

    /**
     *  delete the "id" services.
     */
    public void delete(Long id);
    
    public Page<Services> findAllByNameAndStatus(Pageable pageable,String name , Long statusId);
    
    public List<Services> findAllAvailable();
}
