package com.hotel.app.repository;

import com.hotel.app.domain.Image;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Image entity.
 */
public interface ImageRepository extends JpaRepository<Image,Long> {

}
