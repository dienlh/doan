package com.hotel.app.service.impl;

import com.hotel.app.service.GenderService;
import com.hotel.app.domain.Gender;
import com.hotel.app.repository.GenderRepository;
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
 * Service Implementation for managing Gender.
 */
@Service
@Transactional
public class GenderServiceImpl implements GenderService{

    private final Logger log = LoggerFactory.getLogger(GenderServiceImpl.class);
    
    @Inject
    private GenderRepository genderRepository;
    
    /**
     * Save a gender.
     * @return the persisted entity
     */
    public Gender save(Gender gender) {
        log.debug("Request to save Gender : {}", gender);
        Gender result = genderRepository.save(gender);
        return result;
    }

    /**
     *  get all the genders.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Gender> findAll(Pageable pageable) {
        log.debug("Request to get all Genders");
        Page<Gender> result = genderRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one gender by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Gender findOne(Long id) {
        log.debug("Request to get Gender : {}", id);
        Gender gender = genderRepository.findOne(id);
        return gender;
    }

    /**
     *  delete the  gender by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Gender : {}", id);
        genderRepository.delete(id);
    }
}
