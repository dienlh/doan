package com.hotel.app.repository;

import com.hotel.app.domain.Position;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Position entity.
 */
public interface PositionRepository extends JpaRepository<Position,Long> {

    @Query("select position from Position position where position.create_by.login = ?#{principal.username}")
    List<Position> findByCreate_byIsCurrentUser();

}
