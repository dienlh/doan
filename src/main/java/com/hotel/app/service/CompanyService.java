package com.hotel.app.service;

import com.hotel.app.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     * @return the persisted entity
     */
    public Company save(Company company);

    /**
     *  get all the companys.
     *  @return the list of entities
     */
    public Page<Company> findAll(Pageable pageable);

    /**
     *  get the "id" company.
     *  @return the entity
     */
    public Company findOne(Long id);

    /**
     *  delete the "id" company.
     */
    public void delete(Long id);
}
