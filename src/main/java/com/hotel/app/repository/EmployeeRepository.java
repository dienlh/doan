package com.hotel.app.repository;

import com.hotel.app.domain.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employee entity.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("select employee from Employee employee where employee.create_by.login = ?#{principal.username}")
	List<Employee> findByCreate_byIsCurrentUser();

	@Query("select employee from Employee employee where employee.last_modified_by.login = ?#{principal.username}")
	List<Employee> findByLast_modified_byIsCurrentUser();

	Page<Employee> findAllByEmailContaining(Pageable page,String email);
	
	@Query("select employee from Employee employee where employee.ic_number like %?1% and employee.email like %?2%")
	Page<Employee> findAllByIc_numberAndEmail(Pageable pageable, String ic,String email);
	
	@Query("select employee from Employee employee where employee.ic_number like %?1%")
	Page<Employee> findAllByIc_number(Pageable pageable, String ic);

	@Query("select employee from Employee employee where employee.ic_number like ?1")
	Employee findOneByIc(String ic);
	
	@Query("select employee from Employee employee")
	Page<Employee> findAllByIc_number1(Pageable pageable);
}
