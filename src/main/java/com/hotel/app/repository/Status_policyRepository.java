package com.hotel.app.repository;

import com.hotel.app.domain.Status_policy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_policy entity.
 */
public interface Status_policyRepository extends JpaRepository<Status_policy,Long> {

    @Query("select status_policy from Status_policy status_policy where status_policy.create_by.login = ?#{principal.username}")
    List<Status_policy> findByCreate_byIsCurrentUser();

}
