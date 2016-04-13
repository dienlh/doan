package com.hotel.app.repository;

import com.hotel.app.domain.Gender;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gender entity.
 */
public interface GenderRepository extends JpaRepository<Gender,Long> {

    @Query("select gender from Gender gender where gender.create_by.login = ?#{principal.username}")
    List<Gender> findByCreate_byIsCurrentUser();

}
