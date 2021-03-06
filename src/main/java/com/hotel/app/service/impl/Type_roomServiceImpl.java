package com.hotel.app.service.impl;

import com.hotel.app.service.Type_roomService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Type_room;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Type_roomRepository;
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
 * Service Implementation for managing Type_room.
 */
@Service
@Transactional
public class Type_roomServiceImpl implements Type_roomService{

    private final Logger log = LoggerFactory.getLogger(Type_roomServiceImpl.class);
    
    @Inject
    private Type_roomRepository type_roomRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a type_room.
     * @return the persisted entity
     */
    public Type_room save(Type_room type_room) {
        log.debug("Request to save Type_room : {}", type_room);
        if (type_room.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			type_room.setCreate_by(user);
			log.info("Preshow user" + user);
		}
        Type_room result = type_roomRepository.save(type_room);
        return result;
    }

    /**
     *  get all the type_rooms.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Type_room> findAll(Pageable pageable) {
        log.debug("Request to get all Type_rooms");
        Page<Type_room> result = type_roomRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one type_room by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Type_room findOne(Long id) {
        log.debug("Request to get Type_room : {}", id);
        Type_room type_room = type_roomRepository.findOne(id);
        return type_room;
    }

    /**
     *  delete the  type_room by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Type_room : {}", id);
        type_roomRepository.delete(id);
    }
    
    @Override
    public Type_room findByName(String name) {
    	return type_roomRepository.findByName(name);
    }
}
