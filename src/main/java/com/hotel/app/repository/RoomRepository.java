package com.hotel.app.repository;

import com.hotel.app.domain.Room;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Room entity.
 */
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("select room from Room room where room.create_by.login = ?#{principal.username}")
    List<Room> findByCreate_byIsCurrentUser();

    @Query("select room from Room room where room.last_modified_by.login = ?#{principal.username}")
    List<Room> findByLast_modified_byIsCurrentUser();

    @Query("select distinct room from Room room left join fetch room.imagess left join fetch room.amenitys")
    List<Room> findAllWithEagerRelationships();

    @Query("select room from Room room left join fetch room.imagess left join fetch room.amenitys where room.id =:id")
    Room findOneWithEagerRelationships(@Param("id") Long id);

}
