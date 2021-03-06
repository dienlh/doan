package com.hotel.app.repository;

import com.hotel.app.domain.Job;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Job entity.
 */
public interface JobRepository extends JpaRepository<Job,Long> {

    @Query("select job from Job job where job.create_by.login = ?#{principal.username}")
    List<Job> findByCreate_byIsCurrentUser();

}
