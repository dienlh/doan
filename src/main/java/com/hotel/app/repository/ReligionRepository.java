package com.hotel.app.repository;

import com.hotel.app.domain.Religion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Religion entity.
 */
public interface ReligionRepository extends JpaRepository<Religion,Long> {

}
