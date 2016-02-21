package com.hotel.app.service.impl;

import com.hotel.app.service.Method_paymentService;
import com.hotel.app.domain.Method_payment;
import com.hotel.app.repository.Method_paymentRepository;
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
 * Service Implementation for managing Method_payment.
 */
@Service
@Transactional
public class Method_paymentServiceImpl implements Method_paymentService{

    private final Logger log = LoggerFactory.getLogger(Method_paymentServiceImpl.class);
    
    @Inject
    private Method_paymentRepository method_paymentRepository;
    
    /**
     * Save a method_payment.
     * @return the persisted entity
     */
    public Method_payment save(Method_payment method_payment) {
        log.debug("Request to save Method_payment : {}", method_payment);
        Method_payment result = method_paymentRepository.save(method_payment);
        return result;
    }

    /**
     *  get all the method_payments.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Method_payment> findAll(Pageable pageable) {
        log.debug("Request to get all Method_payments");
        Page<Method_payment> result = method_paymentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one method_payment by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Method_payment findOne(Long id) {
        log.debug("Request to get Method_payment : {}", id);
        Method_payment method_payment = method_paymentRepository.findOne(id);
        return method_payment;
    }

    /**
     *  delete the  method_payment by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Method_payment : {}", id);
        method_paymentRepository.delete(id);
    }
}
