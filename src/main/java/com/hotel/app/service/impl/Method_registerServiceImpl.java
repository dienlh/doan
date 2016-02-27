package com.hotel.app.service.impl;

import com.hotel.app.service.Method_registerService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Method_register;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Method_registerRepository;
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
 * Service Implementation for managing Method_register.
 */
@Service
@Transactional
public class Method_registerServiceImpl implements Method_registerService{

    private final Logger log = LoggerFactory.getLogger(Method_registerServiceImpl.class);
    
    @Inject
    private Method_registerRepository method_registerRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a method_register.
     * @return the persisted entity
     */
    public Method_register save(Method_register method_register) {
        log.debug("Request to save Method_register : {}", method_register);
        if(method_register.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            method_register.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Method_register result = method_registerRepository.save(method_register);
        return result;
    }

    /**
     *  get all the method_registers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Method_register> findAll(Pageable pageable) {
        log.debug("Request to get all Method_registers");
        Page<Method_register> result = method_registerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one method_register by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Method_register findOne(Long id) {
        log.debug("Request to get Method_register : {}", id);
        Method_register method_register = method_registerRepository.findOne(id);
        return method_register;
    }

    /**
     *  delete the  method_register by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Method_register : {}", id);
        method_registerRepository.delete(id);
    }
}
