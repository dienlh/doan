package com.hotel.app.repository;

import com.hotel.app.domain.Profile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Profile entity.
 */
public interface ProfileRepository extends JpaRepository<Profile,Long> {

    @Query("select profile from Profile profile where profile.create_by.login = ?#{principal.username}")
    List<Profile> findByCreate_byIsCurrentUser();

    @Query("select profile from Profile profile where profile.last_modified_by.login = ?#{principal.username}")
    List<Profile> findByLast_modified_byIsCurrentUser();

}
