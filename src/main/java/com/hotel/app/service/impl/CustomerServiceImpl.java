package com.hotel.app.service.impl;

import com.hotel.app.service.CustomerService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Customer;
import com.hotel.app.domain.User;
import com.hotel.app.repository.CustomerRepository;
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
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    
    @Inject
    private CustomerRepository customerRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a customer.
     * @return the persisted entity
     */
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        if(customer.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            customer.setCreate_by(user);
            log.info("Preshow user"+ user);
        }else{
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            User user=new User();
            user.setId(optional.get().getId());
//            user.setLogin(optional.get().getLogin());
            customer.setLast_modified_by(user);
            customer.setLast_modified_date(ZonedDateTime.now());
        }
        Customer result = customerRepository.save(customer);
        return result;
    }

    /**
     *  get all the customers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        Page<Customer> result = customerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one customer by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Customer findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        Customer customer = customerRepository.findOne(id);
        return customer;
    }

    /**
     *  delete the  customer by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.delete(id);
    }
}
