package com.hotel.app.service.impl;

import com.hotel.app.service.BankService;
import com.hotel.app.service.UserService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Bank;
import com.hotel.app.domain.User;
import com.hotel.app.repository.BankRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Bank.
 */
@Service
@Transactional
public class BankServiceImpl implements BankService{

    private final Logger log = LoggerFactory.getLogger(BankServiceImpl.class);
    
    @Inject
    private BankRepository bankRepository;
    
    @Inject
    private UserRepository userRepository;
    
    /**
     * Save a bank.
     * @return the persisted entity
     */
    public Bank save(Bank bank) {
        log.debug("Request to save Bank : {}", bank);   
        if(bank.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            bank.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        
        Bank result = bankRepository.save(bank);
        return result;
    }

    /**
     *  get all the banks.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Bank> findAll(Pageable pageable) {
        log.debug("Request to get all Banks");
        Page<Bank> result = bankRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one bank by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Bank findOne(Long id) {
        log.debug("Request to get Bank : {}", id);
        Bank bank = bankRepository.findOne(id);
        return bank;
    }

    /**
     *  delete the  bank by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bank : {}", id);
        bankRepository.delete(id);
    }
}
