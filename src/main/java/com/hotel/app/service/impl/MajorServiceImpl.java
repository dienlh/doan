package com.hotel.app.service.impl;

import com.hotel.app.service.MajorService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Major;
import com.hotel.app.domain.User;
import com.hotel.app.repository.MajorRepository;
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
 * Service Implementation for managing Major.
 */
@Service
@Transactional
public class MajorServiceImpl implements MajorService{

    private final Logger log = LoggerFactory.getLogger(MajorServiceImpl.class);
    
    @Inject
    private MajorRepository majorRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a major.
     * @return the persisted entity
     */
    public Major save(Major major) {
        log.debug("Request to save Major : {}", major);
        if(major.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            major.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Major result = majorRepository.save(major);
        return result;
    }

    /**
     *  get all the majors.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Major> findAll(Pageable pageable) {
        log.debug("Request to get all Majors");
        Page<Major> result = majorRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one major by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Major findOne(Long id) {
        log.debug("Request to get Major : {}", id);
        Major major = majorRepository.findOne(id);
        return major;
    }

    /**
     *  delete the  major by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Major : {}", id);
        majorRepository.delete(id);
    }
}
