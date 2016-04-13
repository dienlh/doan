package com.hotel.app.repository;

import com.hotel.app.domain.Method_payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Method_payment entity.
 */
public interface Method_paymentRepository extends JpaRepository<Method_payment,Long> {

    @Query("select method_payment from Method_payment method_payment where method_payment.create_by.login = ?#{principal.username}")
    List<Method_payment> findByCreate_byIsCurrentUser();

    Method_payment findByName(String name);
}
