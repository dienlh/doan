package com.hotel.app.service.impl;

import com.hotel.app.service.ReligionService;
import com.hotel.app.domain.Religion;
import com.hotel.app.repository.ReligionRepository;
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
 * Service Implementation for managing Religion.
 */
@Service
@Transactional
public class ReligionServiceImpl implements ReligionService{

    private final Logger log = LoggerFactory.getLogger(ReligionServiceImpl.class);
    
    @Inject
    private ReligionRepository religionRepository;
    
    /**
     * Save a religion.
     * @return the persisted entity
     */
    public Religion save(Religion religion) {
        log.debug("Request to save Religion : {}", religion);
        Religion result = religionRepository.save(religion);
        return result;
    }

    /**
     *  get all the religions.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Religion> findAll(Pageable pageable) {
        log.debug("Request to get all Religions");
        Page<Religion> result = religionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one religion by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Religion findOne(Long id) {
        log.debug("Request to get Religion : {}", id);
        Religion religion = religionRepository.findOne(id);
        return religion;
    }

    /**
     *  delete the  religion by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Religion : {}", id);
        religionRepository.delete(id);
    }
}
