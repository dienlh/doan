package com.hotel.app.repository;

import com.hotel.app.domain.Status_payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_payment entity.
 */
public interface Status_paymentRepository extends JpaRepository<Status_payment,Long> {

    @Query("select status_payment from Status_payment status_payment where status_payment.create_by.login = ?#{principal.username}")
    List<Status_payment> findByCreate_byIsCurrentUser();

}
