package com.hotel.app.service;

import com.hotel.app.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Job.
 */
public interface JobService {

    /**
     * Save a job.
     * @return the persisted entity
     */
    public Job save(Job job);

    /**
     *  get all the jobs.
     *  @return the list of entities
     */
    public Page<Job> findAll(Pageable pageable);

    /**
     *  get the "id" job.
     *  @return the entity
     */
    public Job findOne(Long id);

    /**
     *  delete the "id" job.
     */
    public void delete(Long id);
}
