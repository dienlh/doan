package com.hotel.app.service.impl;

import com.hotel.app.service.Education_levelService;
import com.hotel.app.domain.Education_level;
import com.hotel.app.repository.Education_levelRepository;
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
 * Service Implementation for managing Education_level.
 */
@Service
@Transactional
public class Education_levelServiceImpl implements Education_levelService{

    private final Logger log = LoggerFactory.getLogger(Education_levelServiceImpl.class);
    
    @Inject
    private Education_levelRepository education_levelRepository;
    
    /**
     * Save a education_level.
     * @return the persisted entity
     */
    public Education_level save(Education_level education_level) {
        log.debug("Request to save Education_level : {}", education_level);
        Education_level result = education_levelRepository.save(education_level);
        return result;
    }

    /**
     *  get all the education_levels.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Education_level> findAll(Pageable pageable) {
        log.debug("Request to get all Education_levels");
        Page<Education_level> result = education_levelRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one education_level by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Education_level findOne(Long id) {
        log.debug("Request to get Education_level : {}", id);
        Education_level education_level = education_levelRepository.findOne(id);
        return education_level;
    }

    /**
     *  delete the  education_level by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Education_level : {}", id);
        education_levelRepository.delete(id);
    }
}
