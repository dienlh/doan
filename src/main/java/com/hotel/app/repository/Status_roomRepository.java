package com.hotel.app.repository;

import com.hotel.app.domain.Status_room;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_room entity.
 */
public interface Status_roomRepository extends JpaRepository<Status_room,Long> {

}
