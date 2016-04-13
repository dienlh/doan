package com.hotel.app.service.impl;

import com.hotel.app.service.Type_policyService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Type_policy;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Type_policyRepository;
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
 * Service Implementation for managing Type_policy.
 */
@Service
@Transactional
public class Type_policyServiceImpl implements Type_policyService{

    private final Logger log = LoggerFactory.getLogger(Type_policyServiceImpl.class);
    
    @Inject
    private Type_policyRepository type_policyRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a type_policy.
     * @return the persisted entity
     */
    public Type_policy save(Type_policy type_policy) {
        log.debug("Request to save Type_policy : {}", type_policy);
        if(type_policy.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            type_policy.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Type_policy result = type_policyRepository.save(type_policy);
        return result;
    }

    /**
     *  get all the type_policys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Type_policy> findAll(Pageable pageable) {
        log.debug("Request to get all Type_policys");
        Page<Type_policy> result = type_policyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one type_policy by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Type_policy findOne(Long id) {
        log.debug("Request to get Type_policy : {}", id);
        Type_policy type_policy = type_policyRepository.findOne(id);
        return type_policy;
    }

    /**
     *  delete the  type_policy by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Type_policy : {}", id);
        type_policyRepository.delete(id);
    }
}
