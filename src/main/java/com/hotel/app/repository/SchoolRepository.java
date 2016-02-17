package com.hotel.app.repository;

import com.hotel.app.domain.School;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the School entity.
 */
public interface SchoolRepository extends JpaRepository<School,Long> {

}
