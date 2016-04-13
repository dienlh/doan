package com.hotel.app.service.impl;

import com.hotel.app.service.CompanyService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Company;
import com.hotel.app.domain.User;
import com.hotel.app.repository.CompanyRepository;
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
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
    
    @Inject
    private CompanyRepository companyRepository;
    
    @Inject
    private UserRepository userRepository;
    /**
     * Save a company.
     * @return the persisted entity
     */
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        if(company.getId()==null){
        	Optional<ManagedUserDTO> optional=userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
                    .map(ManagedUserDTO::new);
            
            User user=new User();
            user.setId(optional.get().getId());
            user.setLogin(optional.get().getLogin());
            company.setCreate_by(user);
            log.info("Preshow user"+ user);
        }
        Company result = companyRepository.save(company);
        return result;
    }

    /**
     *  get all the companys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Company> findAll(Pageable pageable) {
        log.debug("Request to get all Companys");
        Page<Company> result = companyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one company by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Company findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOne(id);
        return company;
    }

    /**
     *  delete the  company by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
    }
}
