package com.hotel.app.repository;

import com.hotel.app.domain.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select customer from Customer customer where customer.create_by.login = ?#{principal.username}")
    List<Customer> findByCreate_byIsCurrentUser();

    @Query("select customer from Customer customer where customer.last_modified_by.login = ?#{principal.username}")
    List<Customer> findByLast_modified_byIsCurrentUser();

    @Query("select customer from Customer customer where customer.ic_passport_number = ?1")
    Customer findByIcPassPortNumber(String ic_passport_number);
    
    @Query("select customer from Customer customer where customer.ic_passport_number like %?1%")
    Page<Customer> findAllByIcPassportNumber(Pageable pageable,String ic_passport_number);
    
    @Query("select customer from Customer customer where ic_passport_number like %:ipnumber%")
    Page<Customer> findByLastnameOrFirstname(Pageable pageable, @Param("ipnumber") String lastname);
}
