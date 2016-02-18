package com.hotel.app.service.impl;

import com.hotel.app.service.KindService;
import com.hotel.app.domain.Kind;
import com.hotel.app.repository.KindRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Kind.
 */
@Service
@Transactional
public class KindServiceImpl implements KindService{

    private final Logger log = LoggerFactory.getLogger(KindServiceImpl.class);
    
    @Inject
    private KindRepository kindRepository;
    
    /**
     * Save a kind.
     * @return the persisted entity
     */
    public Kind save(Kind kind) {
        log.debug("Request to save Kind : {}", kind);
        Kind result = kindRepository.save(kind);
        return result;
    }

    /**
     *  get all the kinds.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Kind> findAll(Pageable pageable) {
        log.debug("Request to get all Kinds");
        Page<Kind> result = kindRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one kind by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Kind findOne(Long id) {
        log.debug("Request to get Kind : {}", id);
        Kind kind = kindRepository.findOneWithEagerRelationships(id);
        return kind;
    }

    /**
     *  delete the  kind by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Kind : {}", id);
        kindRepository.delete(id);
    }
}
