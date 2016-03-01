package com.hotel.app.service.impl;

import com.hotel.app.service.Kind_of_policyService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Kind_of_policy;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Kind_of_policyRepository;
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
 * Service Implementation for managing Kind_of_policy.
 */
@Service
@Transactional
public class Kind_of_policyServiceImpl implements Kind_of_policyService{

    private final Logger log = LoggerFactory.getLogger(Kind_of_policyServiceImpl.class);
    
    @Inject
    private Kind_of_policyRepository kind_of_policyRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a kind_of_policy.
     * @return the persisted entity
     */
    public Kind_of_policy save(Kind_of_policy kind_of_policy) {
        log.debug("Request to save Kind_of_policy : {}", kind_of_policy);
        if(kind_of_policy.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            kind_of_policy.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Kind_of_policy result = kind_of_policyRepository.save(kind_of_policy);
        return result;
    }

    /**
     *  get all the kind_of_policys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Kind_of_policy> findAll(Pageable pageable) {
        log.debug("Request to get all Kind_of_policys");
        Page<Kind_of_policy> result = kind_of_policyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one kind_of_policy by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Kind_of_policy findOne(Long id) {
        log.debug("Request to get Kind_of_policy : {}", id);
        Kind_of_policy kind_of_policy = kind_of_policyRepository.findOne(id);
        return kind_of_policy;
    }

    /**
     *  delete the  kind_of_policy by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Kind_of_policy : {}", id);
        kind_of_policyRepository.delete(id);
    }
}
