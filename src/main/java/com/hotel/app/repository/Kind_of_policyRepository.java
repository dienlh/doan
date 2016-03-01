package com.hotel.app.repository;

import com.hotel.app.domain.Kind_of_policy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Kind_of_policy entity.
 */
public interface Kind_of_policyRepository extends JpaRepository<Kind_of_policy,Long> {

    @Query("select kind_of_policy from Kind_of_policy kind_of_policy where kind_of_policy.create_by.login = ?#{principal.username}")
    List<Kind_of_policy> findByCreate_byIsCurrentUser();

}
