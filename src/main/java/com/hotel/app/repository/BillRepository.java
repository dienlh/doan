package com.hotel.app.repository;

import com.hotel.app.domain.Bill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bill entity.
 */
public interface BillRepository extends JpaRepository<Bill,Long> {

    @Query("select bill from Bill bill where bill.create_by.login = ?#{principal.username}")
    List<Bill> findByCreate_byIsCurrentUser();

}
