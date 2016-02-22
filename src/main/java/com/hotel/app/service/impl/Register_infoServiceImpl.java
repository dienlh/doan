package com.hotel.app.service.impl;

import com.hotel.app.service.Register_infoService;
import com.hotel.app.domain.Register_info;
import com.hotel.app.repository.Register_infoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Register_info.
 */
@Service
@Transactional
public class Register_infoServiceImpl implements Register_infoService{

    private final Logger log = LoggerFactory.getLogger(Register_infoServiceImpl.class);
    
    @Inject
    private Register_infoRepository register_infoRepository;
    
    /**
     * Save a register_info.
     * @return the persisted entity
     */
    public Register_info save(Register_info register_info) {
        log.debug("Request to save Register_info : {}", register_info);
        Register_info result = register_infoRepository.save(register_info);
        return result;
    }

    /**
     *  get all the register_infos.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Register_info> findAll(Pageable pageable) {
        log.debug("Request to get all Register_infos");
        Page<Register_info> result = register_infoRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the register_infos where Reservation is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Register_info> findAllWhereReservationIsNull() {
        log.debug("Request to get all register_infos where Reservation is null");
        return StreamSupport
            .stream(register_infoRepository.findAll().spliterator(), false)
            .filter(register_info -> register_info.getReservation() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one register_info by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Register_info findOne(Long id) {
        log.debug("Request to get Register_info : {}", id);
        Register_info register_info = register_infoRepository.findOne(id);
        return register_info;
    }

    /**
     *  delete the  register_info by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Register_info : {}", id);
        register_infoRepository.delete(id);
    }
}
