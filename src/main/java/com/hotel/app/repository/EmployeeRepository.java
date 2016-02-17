package com.hotel.app.repository;

import com.hotel.app.domain.Employee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employee entity.
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("select employee from Employee employee where employee.create_by.login = ?#{principal.username}")
    List<Employee> findByCreate_byIsCurrentUser();

    @Query("select employee from Employee employee where employee.last_modified_by.login = ?#{principal.username}")
    List<Employee> findByLast_modified_byIsCurrentUser();

}
