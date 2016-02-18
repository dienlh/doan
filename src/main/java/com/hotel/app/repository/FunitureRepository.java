package com.hotel.app.repository;

import com.hotel.app.domain.Funiture;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Funiture entity.
 */
public interface FunitureRepository extends JpaRepository<Funiture,Long> {

    @Query("select funiture from Funiture funiture where funiture.create_by.login = ?#{principal.username}")
    List<Funiture> findByCreate_byIsCurrentUser();

}
