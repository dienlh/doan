package com.hotel.app.repository;

import com.hotel.app.domain.Amenity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Amenity entity.
 */
public interface AmenityRepository extends JpaRepository<Amenity,Long> {

    @Query("select amenity from Amenity amenity where amenity.create_by.login = ?#{principal.username}")
    List<Amenity> findByCreate_byIsCurrentUser();

}
