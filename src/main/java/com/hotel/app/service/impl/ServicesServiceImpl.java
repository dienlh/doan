package com.hotel.app.service.impl;

import com.hotel.app.service.ServicesService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Services;
import com.hotel.app.domain.User;
import com.hotel.app.repository.ServicesRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Services.
 */
@Service
@Transactional
public class ServicesServiceImpl implements ServicesService {

	private final Logger log = LoggerFactory.getLogger(ServicesServiceImpl.class);

	@Inject
	private ServicesRepository servicesRepository;

	@Inject
	private UserRepository userRepository;

	/**
	 * Save a services.
	 * 
	 * @return the persisted entity
	 */
	public Services save(Services services) {
		log.debug("Request to save Services : {}", services);
		if (services.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			services.setCreate_by(user);
			log.info("Preshow user" + user);
		} else {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			// user.setLogin(optional.get().getLogin());
			services.setLast_modified_by(user);
			services.setLast_modified_date(ZonedDateTime.now());
		}
		Services result = servicesRepository.save(services);
		return result;
	}

	/**
	 * get all the servicess.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Services> findAll(Pageable pageable) {
		log.debug("Request to get all Servicess");
		Page<Services> result = servicesRepository.findAll(pageable);
		return result;
	}

	/**
	 * get one services by id.
	 * 
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Services findOne(Long id) {
		log.debug("Request to get Services : {}", id);
		Services services = servicesRepository.findOne(id);
		return services;
	}

	/**
	 * delete the services by id.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Services : {}", id);
		servicesRepository.delete(id);
	}
}
