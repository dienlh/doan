package com.hotel.app.repository;

import com.hotel.app.domain.Status_service;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_service entity.
 */
public interface Status_serviceRepository extends JpaRepository<Status_service,Long> {

    @Query("select status_service from Status_service status_service where status_service.create_by.login = ?#{principal.username}")
    List<Status_service> findByCreate_byIsCurrentUser();

}
