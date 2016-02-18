package com.hotel.app.service.impl;

import com.hotel.app.service.Status_profileService;
import com.hotel.app.domain.Status_profile;
import com.hotel.app.repository.Status_profileRepository;
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
 * Service Implementation for managing Status_profile.
 */
@Service
@Transactional
public class Status_profileServiceImpl implements Status_profileService{

    private final Logger log = LoggerFactory.getLogger(Status_profileServiceImpl.class);
    
    @Inject
    private Status_profileRepository status_profileRepository;
    
    /**
     * Save a status_profile.
     * @return the persisted entity
     */
    public Status_profile save(Status_profile status_profile) {
        log.debug("Request to save Status_profile : {}", status_profile);
        Status_profile result = status_profileRepository.save(status_profile);
        return result;
    }

    /**
     *  get all the status_profiles.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Status_profile> findAll(Pageable pageable) {
        log.debug("Request to get all Status_profiles");
        Page<Status_profile> result = status_profileRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one status_profile by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status_profile findOne(Long id) {
        log.debug("Request to get Status_profile : {}", id);
        Status_profile status_profile = status_profileRepository.findOne(id);
        return status_profile;
    }

    /**
     *  delete the  status_profile by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status_profile : {}", id);
        status_profileRepository.delete(id);
    }
}
