package com.hotel.app.service.impl;

import com.hotel.app.service.PositionService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Position;
import com.hotel.app.domain.User;
import com.hotel.app.repository.PositionRepository;
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
 * Service Implementation for managing Position.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService{

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);
    
    @Inject
    private PositionRepository positionRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a position.
     * @return the persisted entity
     */
    public Position save(Position position) {
        log.debug("Request to save Position : {}", position);
        if(position.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            position.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Position result = positionRepository.save(position);
        return result;
    }

    /**
     *  get all the positions.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Position> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        Page<Position> result = positionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one position by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Position findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        Position position = positionRepository.findOne(id);
        return position;
    }

    /**
     *  delete the  position by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.delete(id);
    }
}
