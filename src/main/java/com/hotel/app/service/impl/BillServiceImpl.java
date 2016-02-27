package com.hotel.app.service.impl;

import com.hotel.app.service.BillService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Bill;
import com.hotel.app.domain.User;
import com.hotel.app.repository.BillRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

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
 * Service Implementation for managing Bill.
 */
@Service
@Transactional
public class BillServiceImpl implements BillService{

    private final Logger log = LoggerFactory.getLogger(BillServiceImpl.class);
    
    @Inject
    private BillRepository billRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a bill.
     * @return the persisted entity
     */
    public Bill save(Bill bill) {
        log.debug("Request to save Bill : {}", bill);
        if(bill.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            bill.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Bill result = billRepository.save(bill);
        return result;
    }

    /**
     *  get all the bills.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Bill> findAll(Pageable pageable) {
        log.debug("Request to get all Bills");
        Page<Bill> result = billRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one bill by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Bill findOne(Long id) {
        log.debug("Request to get Bill : {}", id);
        Bill bill = billRepository.findOne(id);
        return bill;
    }

    /**
     *  delete the  bill by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bill : {}", id);
        billRepository.delete(id);
    }
}
