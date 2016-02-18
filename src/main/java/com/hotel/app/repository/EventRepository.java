package com.hotel.app.repository;

import com.hotel.app.domain.Event;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Event entity.
 */
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("select event from Event event where event.create_by.login = ?#{principal.username}")
    List<Event> findByCreate_byIsCurrentUser();

    @Query("select event from Event event where event.last_modified_by.login = ?#{principal.username}")
    List<Event> findByLast_modified_byIsCurrentUser();

}
