package com.hotel.app.service;

import com.hotel.app.domain.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Policy.
 */
public interface PolicyService {

    /**
     * Save a policy.
     * @return the persisted entity
     */
    public Policy save(Policy policy);

    /**
     *  get all the policys.
     *  @return the list of entities
     */
    public Page<Policy> findAll(Pageable pageable);

    /**
     *  get the "id" policy.
     *  @return the entity
     */
    public Policy findOne(Long id);

    /**
     *  delete the "id" policy.
     */
    public void delete(Long id);
}
