package com.hotel.app.repository;

import com.hotel.app.domain.Came_component;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Came_component entity.
 */
public interface Came_componentRepository extends JpaRepository<Came_component,Long> {

    @Query("select came_component from Came_component came_component where came_component.create_by.login = ?#{principal.username}")
    List<Came_component> findByCreate_byIsCurrentUser();

}
