package com.hotel.app.service.impl;

import com.hotel.app.service.Status_peService;
import com.hotel.app.domain.Status_pe;
import com.hotel.app.repository.Status_peRepository;
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
 * Service Implementation for managing Status_pe.
 */
@Service
@Transactional
public class Status_peServiceImpl implements Status_peService{

    private final Logger log = LoggerFactory.getLogger(Status_peServiceImpl.class);
    
    @Inject
    private Status_peRepository status_peRepository;
    
    /**
     * Save a status_pe.
     * @return the persisted entity
     */
    public Status_pe save(Status_pe status_pe) {
        log.debug("Request to save Status_pe : {}", status_pe);
        Status_pe result = status_peRepository.save(status_pe);
        return result;
    }

    /**
     *  get all the status_pes.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_pe> findAll(Pageable pageable) {
        log.debug("Request to get all Status_pes");
        Page<Status_pe> result = status_peRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_pe by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_pe findOne(Long id) {
        log.debug("Request to get Status_pe : {}", id);
        Status_pe status_pe = status_peRepository.findOne(id);
        return status_pe;
    }

    /**
     *  delete the  status_pe by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_pe : {}", id);
        status_peRepository.delete(id);
    }
}
