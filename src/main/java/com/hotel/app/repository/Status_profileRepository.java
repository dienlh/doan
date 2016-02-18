package com.hotel.app.repository;

import com.hotel.app.domain.Status_profile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_profile entity.
 */
public interface Status_profileRepository extends JpaRepository<Status_profile,Long> {

}
