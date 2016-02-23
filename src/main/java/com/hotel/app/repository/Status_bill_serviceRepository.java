package com.hotel.app.repository;

import com.hotel.app.domain.Status_bill_service;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_bill_service entity.
 */
public interface Status_bill_serviceRepository extends JpaRepository<Status_bill_service,Long> {

    @Query("select status_bill_service from Status_bill_service status_bill_service where status_bill_service.create_by.login = ?#{principal.username}")
    List<Status_bill_service> findByCreate_byIsCurrentUser();

}
