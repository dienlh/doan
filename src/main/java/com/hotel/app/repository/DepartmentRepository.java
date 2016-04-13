package com.hotel.app.repository;

import com.hotel.app.domain.Department;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Department entity.
 */
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("select department from Department department where department.create_by.login = ?#{principal.username}")
    List<Department> findByCreate_byIsCurrentUser();

}
