package com.hotel.app.service.impl;

import com.hotel.app.service.Came_componentService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Came_component;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Came_componentRepository;
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
 * Service Implementation for managing Came_component.
 */
@Service
@Transactional
public class Came_componentServiceImpl implements Came_componentService{

    private final Logger log = LoggerFactory.getLogger(Came_componentServiceImpl.class);
    
    @Inject
    private Came_componentRepository came_componentRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a came_component.
     * @return the persisted entity
     */
    public Came_component save(Came_component came_component) {
        log.debug("Request to save Came_component : {}", came_component);
        if(came_component.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            came_component.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Came_component result = came_componentRepository.save(came_component);
        return result;
    }

    /**
     *  get all the came_components.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Came_component> findAll(Pageable pageable) {
        log.debug("Request to get all Came_components");
        Page<Came_component> result = came_componentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one came_component by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Came_component findOne(Long id) {
        log.debug("Request to get Came_component : {}", id);
        Came_component came_component = came_componentRepository.findOne(id);
        return came_component;
    }

    /**
     *  delete the  came_component by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Came_component : {}", id);
        came_componentRepository.delete(id);
    }
}
