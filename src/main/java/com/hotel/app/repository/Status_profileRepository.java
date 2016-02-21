package com.hotel.app.repository;

import com.hotel.app.domain.Status_profile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_profile entity.
 */
public interface Status_profileRepository extends JpaRepository<Status_profile,Long> {

    @Query("select status_profile from Status_profile status_profile where status_profile.create_by.login = ?#{principal.username}")
    List<Status_profile> findByCreate_byIsCurrentUser();

}
