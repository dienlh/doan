package com.hotel.app.service.impl;

import com.hotel.app.service.DepartmentService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Department;
import com.hotel.app.domain.User;
import com.hotel.app.repository.DepartmentRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Department.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    
    @Inject
    private DepartmentRepository departmentRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a department.
     * @return the persisted entity
     */
    public Department save(Department department) {
        log.debug("Request to save Department : {}", department);
        if(department.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            department.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Department result = departmentRepository.save(department);
        return result;
    }

    /**
     *  get all the departments.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Department> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        Page<Department> result = departmentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one department by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Department findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        Department department = departmentRepository.findOne(id);
        return department;
    }

    /**
     *  delete the  department by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.delete(id);
    }
}
