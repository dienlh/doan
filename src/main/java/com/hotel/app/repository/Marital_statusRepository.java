package com.hotel.app.repository;

import com.hotel.app.domain.Marital_status;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Marital_status entity.
 */
public interface Marital_statusRepository extends JpaRepository<Marital_status,Long> {

    @Query("select marital_status from Marital_status marital_status where marital_status.create_by.login = ?#{principal.username}")
    List<Marital_status> findByCreate_byIsCurrentUser();

}
