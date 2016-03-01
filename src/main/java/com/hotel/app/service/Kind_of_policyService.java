package com.hotel.app.service;

import com.hotel.app.domain.Kind_of_policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Kind_of_policy.
 */
public interface Kind_of_policyService {

    /**
     * Save a kind_of_policy.
     * @return the persisted entity
     */
    public Kind_of_policy save(Kind_of_policy kind_of_policy);

    /**
     *  get all the kind_of_policys.
     *  @return the list of entities
     */
    public Page<Kind_of_policy> findAll(Pageable pageable);

    /**
     *  get the "id" kind_of_policy.
     *  @return the entity
     */
    public Kind_of_policy findOne(Long id);

    /**
     *  delete the "id" kind_of_policy.
     */
    public void delete(Long id);
}
