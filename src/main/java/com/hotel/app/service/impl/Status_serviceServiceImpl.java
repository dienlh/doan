package com.hotel.app.service.impl;

import com.hotel.app.service.Status_serviceService;
import com.hotel.app.domain.Status_service;
import com.hotel.app.repository.Status_serviceRepository;
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
 * Service Implementation for managing Status_service.
 */
@Service
@Transactional
public class Status_serviceServiceImpl implements Status_serviceService{

    private final Logger log = LoggerFactory.getLogger(Status_serviceServiceImpl.class);
    
    @Inject
    private Status_serviceRepository status_serviceRepository;
    
    /**
     * Save a status_service.
     * @return the persisted entity
     */
    public Status_service save(Status_service status_service) {
        log.debug("Request to save Status_service : {}", status_service);
        Status_service result = status_serviceRepository.save(status_service);
        return result;
    }

    /**
     *  get all the status_services.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_service> findAll(Pageable pageable) {
        log.debug("Request to get all Status_services");
        Page<Status_service> result = status_serviceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_service by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_service findOne(Long id) {
        log.debug("Request to get Status_service : {}", id);
        Status_service status_service = status_serviceRepository.findOne(id);
        return status_service;
    }

    /**
     *  delete the  status_service by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_service : {}", id);
        status_serviceRepository.delete(id);
    }
}
