package com.hotel.app.service.impl;

import com.hotel.app.service.Status_billService;
import com.hotel.app.domain.Status_bill;
import com.hotel.app.repository.Status_billRepository;
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
 * Service Implementation for managing Status_bill.
 */
@Service
@Transactional
public class Status_billServiceImpl implements Status_billService{

    private final Logger log = LoggerFactory.getLogger(Status_billServiceImpl.class);
    
    @Inject
    private Status_billRepository status_billRepository;
    
    /**
     * Save a status_bill.
     * @return the persisted entity
     */
    public Status_bill save(Status_bill status_bill) {
        log.debug("Request to save Status_bill : {}", status_bill);
        Status_bill result = status_billRepository.save(status_bill);
        return result;
    }

    /**
     *  get all the status_bills.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_bill> findAll(Pageable pageable) {
        log.debug("Request to get all Status_bills");
        Page<Status_bill> result = status_billRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_bill by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_bill findOne(Long id) {
        log.debug("Request to get Status_bill : {}", id);
        Status_bill status_bill = status_billRepository.findOne(id);
        return status_bill;
    }

    /**
     *  delete the  status_bill by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_bill : {}", id);
        status_billRepository.delete(id);
    }
}
