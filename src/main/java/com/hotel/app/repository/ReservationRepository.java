package com.hotel.app.repository;

import com.hotel.app.domain.Reservation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Reservation entity.
 */
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("select reservation from Reservation reservation where reservation.create_by.login = ?#{principal.username}")
    List<Reservation> findByCreate_byIsCurrentUser();

    @Query("select reservation from Reservation reservation where reservation.last_modified_by.login = ?#{principal.username}")
    List<Reservation> findByLast_modified_byIsCurrentUser();

    @Query("select distinct reservation from Reservation reservation left join fetch reservation.customers")
    List<Reservation> findAllWithEagerRelationships();

    @Query("select reservation from Reservation reservation left join fetch reservation.customers where reservation.id =:id")
    Reservation findOneWithEagerRelationships(@Param("id") Long id);

}
