package com.hotel.app.repository;

import com.hotel.app.domain.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Services entity.
 */
public interface ServicesRepository extends JpaRepository<Services,Long> {

    @Query("select services from Services services where services.create_by.login = ?#{principal.username}")
    List<Services> findByCreate_byIsCurrentUser();

    @Query("select services from Services services where services.last_modified_by.login = ?#{principal.username}")
    List<Services> findByLast_modified_byIsCurrentUser();

    @Query("select services from Services services "
    		+ "where services.name like %?1% "
    		+ "and (?2=0L or services.status_service.id=?2)")
    Page<Services> findAllByNameAndStatus(Pageable pageable,String name , Long statusId);
    
    @Query("select services from Services services where services.status_service.id=1L")
    List<Services> findAllAvailable();
}
