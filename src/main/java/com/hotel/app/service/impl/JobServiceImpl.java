package com.hotel.app.service.impl;

import com.hotel.app.service.JobService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Job;
import com.hotel.app.domain.User;
import com.hotel.app.repository.JobRepository;
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
 * Service Implementation for managing Job.
 */
@Service
@Transactional
public class JobServiceImpl implements JobService{

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    
    @Inject
    private JobRepository jobRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a job.
     * @return the persisted entity
     */
    public Job save(Job job) {
        log.debug("Request to save Job : {}", job);
        if(job.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            job.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Job result = jobRepository.save(job);
        return result;
    }

    /**
     *  get all the jobs.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Job> findAll(Pageable pageable) {
        log.debug("Request to get all Jobs");
        Page<Job> result = jobRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one job by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Job findOne(Long id) {
        log.debug("Request to get Job : {}", id);
        Job job = jobRepository.findOne(id);
        return job;
    }

    /**
     *  delete the  job by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Job : {}", id);
        jobRepository.delete(id);
    }
}
