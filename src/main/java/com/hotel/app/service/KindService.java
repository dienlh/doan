package com.hotel.app.service;

import com.hotel.app.domain.Kind;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Kind.
 */
public interface KindService {

    /**
     * Save a kind.
     * @return the persisted entity
     */
    public Kind save(Kind kind);

    /**
     *  get all the kinds.
     *  @return the list of entities
     */
    public Page<Kind> findAll(Pageable pageable);

    /**
     *  get the "id" kind.
     *  @return the entity
     */
    public Kind findOne(Long id);

    /**
     *  delete the "id" kind.
     */
    public void delete(Long id);
}
