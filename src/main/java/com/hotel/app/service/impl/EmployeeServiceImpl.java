package com.hotel.app.service.impl;

import com.hotel.app.service.EmployeeService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Employee;
import com.hotel.app.domain.User;
import com.hotel.app.repository.EmployeeRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Employee.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    @Inject
    private EmployeeRepository employeeRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a employee.
     * @return the persisted entity
     */
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        if(employee.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            employee.setCreate_by(user);
            log.info("Preshow user"+ user);
        }else{
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            User user=new User();
            user.setId(optional.get().getId());
            employee.setLast_modified_by(user);
            employee.setLast_modified_date(ZonedDateTime.now());
            log.info("Preshow user"+ user);
        }
        Employee result = employeeRepository.save(employee);
        return result;
    }

    /**
     *  get all the employees.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        Page<Employee> result = employeeRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the employees where Profile is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Employee> findAllWhereProfileIsNull() {
        log.debug("Request to get all employees where Profile is null");
        return StreamSupport
            .stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getProfile() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one employee by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Employee findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        Employee employee = employeeRepository.findOne(id);
        return employee;
    }

    /**
     *  delete the  employee by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.delete(id);
    }
}
