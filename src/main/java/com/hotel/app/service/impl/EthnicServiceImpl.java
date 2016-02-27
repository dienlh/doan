package com.hotel.app.service.impl;

import com.hotel.app.service.EthnicService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Ethnic;
import com.hotel.app.domain.User;
import com.hotel.app.repository.EthnicRepository;
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
 * Service Implementation for managing Ethnic.
 */
@Service
@Transactional
public class EthnicServiceImpl implements EthnicService{

    private final Logger log = LoggerFactory.getLogger(EthnicServiceImpl.class);
    
    @Inject
    private EthnicRepository ethnicRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a ethnic.
     * @return the persisted entity
     */
    public Ethnic save(Ethnic ethnic) {
        log.debug("Request to save Ethnic : {}", ethnic);
        if(ethnic.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            ethnic.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Ethnic result = ethnicRepository.save(ethnic);
        return result;
    }

    /**
     *  get all the ethnics.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Ethnic> findAll(Pageable pageable) {
        log.debug("Request to get all Ethnics");
        Page<Ethnic> result = ethnicRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one ethnic by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Ethnic findOne(Long id) {
        log.debug("Request to get Ethnic : {}", id);
        Ethnic ethnic = ethnicRepository.findOne(id);
        return ethnic;
    }

    /**
     *  delete the  ethnic by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ethnic : {}", id);
        ethnicRepository.delete(id);
    }
}
