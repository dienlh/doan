package com.hotel.app.service.impl;

import com.hotel.app.service.Status_paymentService;
import com.hotel.app.domain.Status_payment;
import com.hotel.app.repository.Status_paymentRepository;
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
 * Service Implementation for managing Status_payment.
 */
@Service
@Transactional
public class Status_paymentServiceImpl implements Status_paymentService{

    private final Logger log = LoggerFactory.getLogger(Status_paymentServiceImpl.class);
    
    @Inject
    private Status_paymentRepository status_paymentRepository;
    
    /**
     * Save a status_payment.
     * @return the persisted entity
     */
    public Status_payment save(Status_payment status_payment) {
        log.debug("Request to save Status_payment : {}", status_payment);
        Status_payment result = status_paymentRepository.save(status_payment);
        return result;
    }

    /**
     *  get all the status_payments.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_payment> findAll(Pageable pageable) {
        log.debug("Request to get all Status_payments");
        Page<Status_payment> result = status_paymentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_payment by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_payment findOne(Long id) {
        log.debug("Request to get Status_payment : {}", id);
        Status_payment status_payment = status_paymentRepository.findOne(id);
        return status_payment;
    }

    /**
     *  delete the  status_payment by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_payment : {}", id);
        status_paymentRepository.delete(id);
    }
}
