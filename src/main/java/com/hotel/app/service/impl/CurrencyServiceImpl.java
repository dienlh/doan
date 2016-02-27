package com.hotel.app.service.impl;

import com.hotel.app.service.CurrencyService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Currency;
import com.hotel.app.domain.User;
import com.hotel.app.repository.CurrencyRepository;
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
 * Service Implementation for managing Currency.
 */
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService{

    private final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    
    @Inject
    private CurrencyRepository currencyRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a currency.
     * @return the persisted entity
     */
    public Currency save(Currency currency) {
        log.debug("Request to save Currency : {}", currency);
        if(currency.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            currency.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Currency result = currencyRepository.save(currency);
        return result;
    }

    /**
     *  get all the currencys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Currency> findAll(Pageable pageable) {
        log.debug("Request to get all Currencys");
        Page<Currency> result = currencyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one currency by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Currency findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        Currency currency = currencyRepository.findOne(id);
        return currency;
    }

    /**
     *  delete the  currency by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepository.delete(id);
    }
}
