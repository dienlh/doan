package com.hotel.app.repository;

import com.hotel.app.domain.Register_info;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Register_info entity.
 */
public interface Register_infoRepository extends JpaRepository<Register_info,Long> {

    @Query("select register_info from Register_info register_info where register_info.create_by.login = ?#{principal.username}")
    List<Register_info> findByCreate_byIsCurrentUser();

    @Query("select register_info from Register_info register_info where register_info.last_modified_by.login = ?#{principal.username}")
    List<Register_info> findByLast_modified_byIsCurrentUser();

}
