package com.hotel.app.repository;

import com.hotel.app.domain.Status_pe;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Status_pe entity.
 */
public interface Status_peRepository extends JpaRepository<Status_pe,Long> {

    @Query("select status_pe from Status_pe status_pe where status_pe.create_by.login = ?#{principal.username}")
    List<Status_pe> findByCreate_byIsCurrentUser();

}
