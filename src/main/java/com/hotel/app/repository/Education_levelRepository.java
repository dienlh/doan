package com.hotel.app.repository;

import com.hotel.app.domain.Education_level;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Education_level entity.
 */
public interface Education_levelRepository extends JpaRepository<Education_level,Long> {

}
