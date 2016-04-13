package com.hotel.app.service.impl;

import com.hotel.app.service.Method_paymentService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Method_payment;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Method_paymentRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
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
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a method_payment.
     * @return the persisted entity
     */
    public Method_payment save(Method_payment method_payment) {
        log.debug("Request to save Method_payment : {}", method_payment);
        if(method_payment.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            method_payment.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
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
    
    @Override
    public Method_payment createPaymentOnline() {
    	Method_payment method_payment = method_paymentRepository.findByName("Online qua Mypay");
    	if(method_payment==null){
    		method_payment.setName("Online qua Mypay");
    		method_payment.setDecription("Phương thức này được tạo bởi hệ thống nhằm phục vụ thanh toán trên website");
    		
    		User user = new User();
    		user.setId(1L);
    		method_payment.setCreate_by(user);
    		
    		method_payment.setCreate_date(ZonedDateTime.now());
    		method_payment=method_paymentRepository.save(method_payment);
    	}
    	return method_payment;
    }
}
