package com.hotel.app.repository;

import com.hotel.app.domain.School;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the School entity.
 */
public interface SchoolRepository extends JpaRepository<School,Long> {

    @Query("select school from School school where school.create_by.login = ?#{principal.username}")
    List<School> findByCreate_byIsCurrentUser();

}
