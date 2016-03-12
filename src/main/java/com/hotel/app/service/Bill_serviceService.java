package com.hotel.app.service;

import com.hotel.app.domain.Bill_service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Bill_service.
 */
public interface Bill_serviceService {

    /**
     * Save a bill_service.
     * @return the persisted entity
     */
    public Bill_service save(Bill_service bill_service);

    /**
     *  get all the bill_services.
     *  @return the list of entities
     */
    public Page<Bill_service> findAll(Pageable pageable);

    /**
     *  get the "id" bill_service.
     *  @return the entity
     */
    public Bill_service findOne(Long id);

    /**
     *  delete the "id" bill_service.
     */
    public void delete(Long id);
    
    public Page<Bill_service> findAllByMultiAttr(Pageable pageable,Long serviceId,Long statusId,Long roomId);
}
