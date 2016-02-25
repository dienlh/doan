package com.hotel.app.service.impl;

import com.hotel.app.service.ProfileService;
import com.hotel.app.domain.Profile;
import com.hotel.app.repository.ProfileRepository;
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
 * Service Implementation for managing Profile.
 */
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService{

    private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);
    
    @Inject
    private ProfileRepository profileRepository;
    
    /**
     * Save a profile.
     * @return the persisted entity
     */
    public Profile save(Profile profile) {
        log.debug("Request to save Profile : {}", profile);
        Profile result = profileRepository.save(profile);
        return result;
    }

    /**
     *  get all the profiles.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Profile> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        Page<Profile> result = profileRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one profile by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Profile findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        Profile profile = profileRepository.findOne(id);
        return profile;
    }

    /**
     *  delete the  profile by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.delete(id);
    }
}
