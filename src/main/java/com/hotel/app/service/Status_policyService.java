package com.hotel.app.service;

import com.hotel.app.domain.Status_policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Status_policy.
 */
public interface Status_policyService {

    /**
     * Save a status_policy.
     * @return the persisted entity
     */
    public Status_policy save(Status_policy status_policy);

    /**
     *  get all the status_policys.
     *  @return the list of entities
     */
    public Page<Status_policy> findAll(Pageable pageable);

    /**
     *  get the "id" status_policy.
     *  @return the entity
     */
    public Status_policy findOne(Long id);

    /**
     *  delete the "id" status_policy.
     */
    public void delete(Long id);
}
