package com.hotel.app.repository;

import com.hotel.app.domain.Type_policy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Type_policy entity.
 */
public interface Type_policyRepository extends JpaRepository<Type_policy,Long> {

    @Query("select type_policy from Type_policy type_policy where type_policy.create_by.login = ?#{principal.username}")
    List<Type_policy> findByCreate_byIsCurrentUser();

}
