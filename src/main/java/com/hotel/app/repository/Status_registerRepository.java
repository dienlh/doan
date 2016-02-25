package com.hotel.app.repository;

import com.hotel.app.domain.Status_register;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_register entity.
 */
public interface Status_registerRepository extends JpaRepository<Status_register,Long> {

    @Query("select status_register from Status_register status_register where status_register.create_by.login = ?#{principal.username}")
    List<Status_register> findByCreate_byIsCurrentUser();

}
