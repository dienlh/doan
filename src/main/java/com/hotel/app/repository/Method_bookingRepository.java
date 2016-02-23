package com.hotel.app.repository;

import com.hotel.app.domain.Method_booking;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Method_booking entity.
 */
public interface Method_bookingRepository extends JpaRepository<Method_booking,Long> {

    @Query("select method_booking from Method_booking method_booking where method_booking.create_by.login = ?#{principal.username}")
    List<Method_booking> findByCreate_byIsCurrentUser();

}
