package com.hotel.app.service;

import com.hotel.app.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Employee.
 */
public interface EmployeeService {

    /**
     * Save a employee.
     * @return the persisted entity
     */
    public Employee save(Employee employee);

    /**
     *  get all the employees.
     *  @return the list of entities
     */
    public Page<Employee> findAll(Pageable pageable);
    /**
     *  get all the employees where Profile is null.
     *  @return the list of entities
     */
    public List<Employee> findAllWhereProfileIsNull();

    /**
     *  get the "id" employee.
     *  @return the entity
     */
    public Employee findOne(Long id);

    /**
     *  delete the "id" employee.
     */
    public void delete(Long id);
}
