package com.hotel.app.repository;

import com.hotel.app.domain.Currency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Currency entity.
 */
public interface CurrencyRepository extends JpaRepository<Currency,Long> {

    @Query("select currency from Currency currency where currency.create_by.login = ?#{principal.username}")
    List<Currency> findByCreate_byIsCurrentUser();

}
