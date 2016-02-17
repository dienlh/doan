package com.hotel.app.repository;

import com.hotel.app.domain.Gender;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gender entity.
 */
public interface GenderRepository extends JpaRepository<Gender,Long> {

}
