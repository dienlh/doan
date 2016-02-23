package com.hotel.app.repository;

import com.hotel.app.domain.Bill_service;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bill_service entity.
 */
public interface Bill_serviceRepository extends JpaRepository<Bill_service,Long> {

    @Query("select bill_service from Bill_service bill_service where bill_service.create_by.login = ?#{principal.username}")
    List<Bill_service> findByCreate_byIsCurrentUser();

}
