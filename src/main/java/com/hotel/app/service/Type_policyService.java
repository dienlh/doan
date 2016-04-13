package com.hotel.app.service;

import com.hotel.app.domain.Type_policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Type_policy.
 */
public interface Type_policyService {

    /**
     * Save a type_policy.
     * @return the persisted entity
     */
    public Type_policy save(Type_policy type_policy);

    /**
     *  get all the type_policys.
     *  @return the list of entities
     */
    public Page<Type_policy> findAll(Pageable pageable);

    /**
     *  get the "id" type_policy.
     *  @return the entity
     */
    public Type_policy findOne(Long id);

    /**
     *  delete the "id" type_policy.
     */
    public void delete(Long id);
}
