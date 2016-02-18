package com.hotel.app.service.impl;

import com.hotel.app.service.FunitureService;
import com.hotel.app.domain.Funiture;
import com.hotel.app.repository.FunitureRepository;
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
 * Service Implementation for managing Funiture.
 */
@Service
@Transactional
public class FunitureServiceImpl implements FunitureService{

    private final Logger log = LoggerFactory.getLogger(FunitureServiceImpl.class);
    
    @Inject
    private FunitureRepository funitureRepository;
    
    /**
     * Save a funiture.
     * @return the persisted entity
     */
    public Funiture save(Funiture funiture) {
        log.debug("Request to save Funiture : {}", funiture);
        Funiture result = funitureRepository.save(funiture);
        return result;
    }

    /**
     *  get all the funitures.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Funiture> findAll(Pageable pageable) {
        log.debug("Request to get all Funitures");
        Page<Funiture> result = funitureRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one funiture by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Funiture findOne(Long id) {
        log.debug("Request to get Funiture : {}", id);
        Funiture funiture = funitureRepository.findOne(id);
        return funiture;
    }

    /**
     *  delete the  funiture by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Funiture : {}", id);
        funitureRepository.delete(id);
    }
}
