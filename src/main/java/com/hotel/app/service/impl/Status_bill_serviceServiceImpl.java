package com.hotel.app.service.impl;

import com.hotel.app.service.Status_bill_serviceService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Status_bill_service;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Status_bill_serviceRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

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
 * Service Implementation for managing Status_bill_service.
 */
@Service
@Transactional
public class Status_bill_serviceServiceImpl implements Status_bill_serviceService{

    private final Logger log = LoggerFactory.getLogger(Status_bill_serviceServiceImpl.class);
    
    @Inject
    private Status_bill_serviceRepository status_bill_serviceRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a status_bill_service.
     * @return the persisted entity
     */
    public Status_bill_service save(Status_bill_service status_bill_service) {
        log.debug("Request to save Status_bill_service : {}", status_bill_service);
        if(status_bill_service.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            status_bill_service.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Status_bill_service result = status_bill_serviceRepository.save(status_bill_service);
        return result;
    }

    /**
     *  get all the status_bill_services.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_bill_service> findAll(Pageable pageable) {
        log.debug("Request to get all Status_bill_services");
        Page<Status_bill_service> result = status_bill_serviceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_bill_service by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_bill_service findOne(Long id) {
        log.debug("Request to get Status_bill_service : {}", id);
        Status_bill_service status_bill_service = status_bill_serviceRepository.findOne(id);
        return status_bill_service;
    }

    /**
     *  delete the  status_bill_service by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_bill_service : {}", id);
        status_bill_serviceRepository.delete(id);
    }
}
