package com.hotel.app.repository;

import com.hotel.app.domain.Images;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Images entity.
 */
public interface ImagesRepository extends JpaRepository<Images,Long> {

}
