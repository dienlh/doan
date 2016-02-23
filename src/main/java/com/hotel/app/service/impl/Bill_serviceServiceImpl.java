package com.hotel.app.service.impl;

import com.hotel.app.service.Bill_serviceService;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.repository.Bill_serviceRepository;
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
 * Service Implementation for managing Bill_service.
 */
@Service
@Transactional
public class Bill_serviceServiceImpl implements Bill_serviceService{

    private final Logger log = LoggerFactory.getLogger(Bill_serviceServiceImpl.class);
    
    @Inject
    private Bill_serviceRepository bill_serviceRepository;
    
    /**
     * Save a bill_service.
     * @return the persisted entity
     */
    public Bill_service save(Bill_service bill_service) {
        log.debug("Request to save Bill_service : {}", bill_service);
        Bill_service result = bill_serviceRepository.save(bill_service);
        return result;
    }

    /**
     *  get all the bill_services.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Bill_service> findAll(Pageable pageable) {
        log.debug("Request to get all Bill_services");
        Page<Bill_service> result = bill_serviceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one bill_service by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Bill_service findOne(Long id) {
        log.debug("Request to get Bill_service : {}", id);
        Bill_service bill_service = bill_serviceRepository.findOne(id);
        return bill_service;
    }

    /**
     *  delete the  bill_service by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bill_service : {}", id);
        bill_serviceRepository.delete(id);
    }
}
