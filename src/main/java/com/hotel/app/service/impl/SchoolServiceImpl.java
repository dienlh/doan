package com.hotel.app.service.impl;

import com.hotel.app.service.SchoolService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.School;
import com.hotel.app.domain.User;
import com.hotel.app.repository.SchoolRepository;
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
 * Service Implementation for managing School.
 */
@Service
@Transactional
public class SchoolServiceImpl implements SchoolService{

    private final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);
    
    @Inject
    private SchoolRepository schoolRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a school.
     * @return the persisted entity
     */
    public School save(School school) {
        log.debug("Request to save School : {}", school);
        if(school.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            school.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        School result = schoolRepository.save(school);
        return result;
    }

    /**
     *  get all the schools.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<School> findAll(Pageable pageable) {
        log.debug("Request to get all Schools");
        Page<School> result = schoolRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one school by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public School findOne(Long id) {
        log.debug("Request to get School : {}", id);
        School school = schoolRepository.findOne(id);
        return school;
    }

    /**
     *  delete the  school by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete School : {}", id);
        schoolRepository.delete(id);
    }
}
