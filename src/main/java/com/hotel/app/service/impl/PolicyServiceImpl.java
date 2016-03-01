package com.hotel.app.service.impl;

import com.hotel.app.service.PolicyService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Policy;
import com.hotel.app.domain.User;
import com.hotel.app.repository.PolicyRepository;
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
 * Service Implementation for managing Policy.
 */
@Service
@Transactional
public class PolicyServiceImpl implements PolicyService{

    private final Logger log = LoggerFactory.getLogger(PolicyServiceImpl.class);
    
    @Inject
    private PolicyRepository policyRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a policy.
     * @return the persisted entity
     */
    public Policy save(Policy policy) {
        log.debug("Request to save Policy : {}", policy);
        if (policy.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			policy.setCreate_by(user);
			log.info("Preshow user" + user);
		} else {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			// user.setLogin(optional.get().getLogin());
			policy.setLast_modified_by(user);
			policy.setLast_modified_date(ZonedDateTime.now());
		}
        Policy result = policyRepository.save(policy);
        return result;
    }

    /**
     *  get all the policys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Policy> findAll(Pageable pageable) {
        log.debug("Request to get all Policys");
        Page<Policy> result = policyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one policy by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Policy findOne(Long id) {
        log.debug("Request to get Policy : {}", id);
        Policy policy = policyRepository.findOne(id);
        return policy;
    }

    /**
     *  delete the  policy by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Policy : {}", id);
        policyRepository.delete(id);
    }
}
