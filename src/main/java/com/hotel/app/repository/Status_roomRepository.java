package com.hotel.app.repository;

import com.hotel.app.domain.Status_room;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_room entity.
 */
public interface Status_roomRepository extends JpaRepository<Status_room,Long> {

    @Query("select status_room from Status_room status_room where status_room.create_by.login = ?#{principal.username}")
    List<Status_room> findByCreate_byIsCurrentUser();

    Status_room findByName(String name);
    
}
