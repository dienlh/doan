package com.hotel.app.repository;

import com.hotel.app.domain.Company;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Company entity.
 */
public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("select company from Company company where company.create_by.login = ?#{principal.username}")
    List<Company> findByCreate_byIsCurrentUser();

    @Query("select company from Company company where company.last_modified_by.login = ?#{principal.username}")
    List<Company> findByLast_modified_byIsCurrentUser();

}
