package com.hotel.app.repository;

import com.hotel.app.domain.Customer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select customer from Customer customer where customer.create_by.login = ?#{principal.username}")
    List<Customer> findByCreate_byIsCurrentUser();

    @Query("select customer from Customer customer where customer.last_modified_by.login = ?#{principal.username}")
    List<Customer> findByLast_modified_byIsCurrentUser();

}
