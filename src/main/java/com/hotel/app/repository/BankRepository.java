package com.hotel.app.repository;

import com.hotel.app.domain.Bank;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bank entity.
 */
public interface BankRepository extends JpaRepository<Bank,Long> {

    @Query("select bank from Bank bank where bank.create_by.login = ?#{principal.username}")
    List<Bank> findByCreate_byIsCurrentUser();

}
