package com.hotel.app.repository;

import com.hotel.app.domain.Major;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Major entity.
 */
public interface MajorRepository extends JpaRepository<Major,Long> {

}
