package com.hotel.app.service.impl;

import com.hotel.app.service.Status_registerService;
import com.hotel.app.domain.Status_register;
import com.hotel.app.repository.Status_registerRepository;
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
 * Service Implementation for managing Status_register.
 */
@Service
@Transactional
public class Status_registerServiceImpl implements Status_registerService{

    private final Logger log = LoggerFactory.getLogger(Status_registerServiceImpl.class);
    
    @Inject
    private Status_registerRepository status_registerRepository;
    
    /**
     * Save a status_register.
     * @return the persisted entity
     */
    public Status_register save(Status_register status_register) {
        log.debug("Request to save Status_register : {}", status_register);
        Status_register result = status_registerRepository.save(status_register);
        return result;
    }

    /**
     *  get all the status_registers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_register> findAll(Pageable pageable) {
        log.debug("Request to get all Status_registers");
        Page<Status_register> result = status_registerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_register by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_register findOne(Long id) {
        log.debug("Request to get Status_register : {}", id);
        Status_register status_register = status_registerRepository.findOne(id);
        return status_register;
    }

    /**
     *  delete the  status_register by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_register : {}", id);
        status_registerRepository.delete(id);
    }
}
