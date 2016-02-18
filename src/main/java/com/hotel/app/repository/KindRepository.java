package com.hotel.app.repository;

import com.hotel.app.domain.Kind;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Kind entity.
 */
public interface KindRepository extends JpaRepository<Kind,Long> {

    @Query("select kind from Kind kind where kind.create_by.login = ?#{principal.username}")
    List<Kind> findByCreate_byIsCurrentUser();

    @Query("select distinct kind from Kind kind left join fetch kind.policys left join fetch kind.events")
    List<Kind> findAllWithEagerRelationships();

    @Query("select kind from Kind kind left join fetch kind.policys left join fetch kind.events where kind.id =:id")
    Kind findOneWithEagerRelationships(@Param("id") Long id);

}
