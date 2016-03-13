package com.hotel.app.repository;

import com.hotel.app.domain.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Event entity.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

	@Query("select event from Event event where event.create_by.login = ?#{principal.username}")
	List<Event> findByCreate_byIsCurrentUser();

	@Query("select event from Event event where event.last_modified_by.login = ?#{principal.username}")
	List<Event> findByLast_modified_byIsCurrentUser();

	@Query("select event from Event event where (?3=0L or event.status_event.id=?3) and (?1 = null and ?2 = null or event.start_date between ?1 and ?2)")
	Page<Event> findAllByRangerDateAndStatus(Pageable pageable, LocalDate fromDate, LocalDate toDate, Long statusId);
	
	@Query("select event from Event event where (?1=0L or event.status_event.id=?1)")
	Page<Event> findAllByStatusId(Pageable pageable, Long statusId);
	
}
