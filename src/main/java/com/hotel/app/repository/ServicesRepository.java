package com.hotel.app.repository;

import com.hotel.app.domain.Services;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Services entity.
 */
public interface ServicesRepository extends JpaRepository<Services,Long> {

    @Query("select services from Services services where services.create_by.login = ?#{principal.username}")
    List<Services> findByCreate_byIsCurrentUser();

    @Query("select services from Services services where services.last_modified_by.login = ?#{principal.username}")
    List<Services> findByLast_modified_byIsCurrentUser();

}
