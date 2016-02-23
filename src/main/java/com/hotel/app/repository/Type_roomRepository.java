package com.hotel.app.repository;

import com.hotel.app.domain.Type_room;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Type_room entity.
 */
public interface Type_roomRepository extends JpaRepository<Type_room,Long> {

    @Query("select type_room from Type_room type_room where type_room.create_by.login = ?#{principal.username}")
    List<Type_room> findByCreate_byIsCurrentUser();

}
