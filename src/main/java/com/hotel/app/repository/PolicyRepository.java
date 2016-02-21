package com.hotel.app.repository;

import com.hotel.app.domain.Policy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Policy entity.
 */
public interface PolicyRepository extends JpaRepository<Policy,Long> {

    @Query("select policy from Policy policy where policy.create_by.login = ?#{principal.username}")
    List<Policy> findByCreate_byIsCurrentUser();

    @Query("select policy from Policy policy where policy.last_modified_by.login = ?#{principal.username}")
    List<Policy> findByLast_modified_byIsCurrentUser();

}
