package com.hotel.app.repository;

import com.hotel.app.domain.Status_bill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_bill entity.
 */
public interface Status_billRepository extends JpaRepository<Status_bill,Long> {

    @Query("select status_bill from Status_bill status_bill where status_bill.create_by.login = ?#{principal.username}")
    List<Status_bill> findByCreate_byIsCurrentUser();

}
