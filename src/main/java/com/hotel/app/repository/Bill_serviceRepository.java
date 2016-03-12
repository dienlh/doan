package com.hotel.app.repository;

import com.hotel.app.domain.Bill_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bill_service entity.
 */
public interface Bill_serviceRepository extends JpaRepository<Bill_service,Long> {

    @Query("select bill_service from Bill_service bill_service where bill_service.create_by.login = ?#{principal.username}")
    List<Bill_service> findByCreate_byIsCurrentUser();

    @Query("select bill_service from Bill_service bill_service "
    		+ "where (?1=0L or bill_service.services.id=?1)"
    		+ "and (?2=0L or bill_service.status_bill_service.id=?2)"
    		+ "and (?3=0L or bill_service.reservation.register_info.room.id=?3)")
    Page<Bill_service> findAllByMultiAttr(Pageable pageable,Long serviceId,Long statusId,Long roomId);
}
