package com.hotel.app.repository;

import com.hotel.app.domain.Status_reservation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_reservation entity.
 */
public interface Status_reservationRepository extends JpaRepository<Status_reservation,Long> {

    @Query("select status_reservation from Status_reservation status_reservation where status_reservation.create_by.login = ?#{principal.username}")
    List<Status_reservation> findByCreate_byIsCurrentUser();

}
