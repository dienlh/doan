package com.hotel.app.repository;

import com.hotel.app.domain.Status_event;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_event entity.
 */
public interface Status_eventRepository extends JpaRepository<Status_event,Long> {

    @Query("select status_event from Status_event status_event where status_event.create_by.login = ?#{principal.username}")
    List<Status_event> findByCreate_byIsCurrentUser();

}
