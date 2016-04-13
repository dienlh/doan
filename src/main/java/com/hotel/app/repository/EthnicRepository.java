package com.hotel.app.repository;

import com.hotel.app.domain.Ethnic;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ethnic entity.
 */
public interface EthnicRepository extends JpaRepository<Ethnic,Long> {

    @Query("select ethnic from Ethnic ethnic where ethnic.create_by.login = ?#{principal.username}")
    List<Ethnic> findByCreate_byIsCurrentUser();

}
