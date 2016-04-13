package com.hotel.app.service.impl;

import com.hotel.app.service.Status_policyService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Status_policy;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Status_policyRepository;
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
 * Service Implementation for managing Status_policy.
 */
@Service
@Transactional
public class Status_policyServiceImpl implements Status_policyService{

    private final Logger log = LoggerFactory.getLogger(Status_policyServiceImpl.class);
    
    @Inject
    private Status_policyRepository status_policyRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a status_policy.
     * @return the persisted entity
     */
    public Status_policy save(Status_policy status_policy) {
        log.debug("Request to save Status_policy : {}", status_policy);
        if(status_policy.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            status_policy.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Status_policy result = status_policyRepository.save(status_policy);
        return result;
    }

    /**
     *  get all the status_policys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_policy> findAll(Pageable pageable) {
        log.debug("Request to get all Status_policys");
        Page<Status_policy> result = status_policyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_policy by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_policy findOne(Long id) {
        log.debug("Request to get Status_policy : {}", id);
        Status_policy status_policy = status_policyRepository.findOne(id);
        return status_policy;
    }

    /**
     *  delete the  status_policy by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_policy : {}", id);
        status_policyRepository.delete(id);
    }
}
