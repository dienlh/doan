package com.hotel.app.repository;

import com.hotel.app.domain.Kind;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Kind entity.
 */
public interface KindRepository extends JpaRepository<Kind,Long> {

    @Query("select kind from Kind kind where kind.create_by.login = ?#{principal.username}")
    List<Kind> findByCreate_byIsCurrentUser();

}
