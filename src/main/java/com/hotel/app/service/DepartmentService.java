package com.hotel.app.service;

import com.hotel.app.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Department.
 */
public interface DepartmentService {

    /**
     * Save a department.
     * @return the persisted entity
     */
    public Department save(Department department);

    /**
     *  get all the departments.
     *  @return the list of entities
     */
    public Page<Department> findAll(Pageable pageable);

    /**
     *  get the "id" department.
     *  @return the entity
     */
    public Department findOne(Long id);

    /**
     *  delete the "id" department.
     */
    public void delete(Long id);
}
