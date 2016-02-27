package com.hotel.app.service.impl;

import com.hotel.app.service.Marital_statusService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Marital_status;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Marital_statusRepository;
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
 * Service Implementation for managing Marital_status.
 */
@Service
@Transactional
public class Marital_statusServiceImpl implements Marital_statusService{

    private final Logger log = LoggerFactory.getLogger(Marital_statusServiceImpl.class);
    
    @Inject
    private Marital_statusRepository marital_statusRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a marital_status.
     * @return the persisted entity
     */
    public Marital_status save(Marital_status marital_status) {
        log.debug("Request to save Marital_status : {}", marital_status);
        if(marital_status.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            marital_status.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Marital_status result = marital_statusRepository.save(marital_status);
        return result;
    }

    /**
     *  get all the marital_statuss.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Marital_status> findAll(Pageable pageable) {
        log.debug("Request to get all Marital_statuss");
        Page<Marital_status> result = marital_statusRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one marital_status by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Marital_status findOne(Long id) {
        log.debug("Request to get Marital_status : {}", id);
        Marital_status marital_status = marital_statusRepository.findOne(id);
        return marital_status;
    }

    /**
     *  delete the  marital_status by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Marital_status : {}", id);
        marital_statusRepository.delete(id);
    }
}
