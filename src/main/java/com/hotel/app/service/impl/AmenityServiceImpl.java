package com.hotel.app.service.impl;

import com.hotel.app.service.AmenityService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Amenity;
import com.hotel.app.domain.User;
import com.hotel.app.repository.AmenityRepository;
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
 * Service Implementation for managing Amenity.
 */
@Service
@Transactional
public class AmenityServiceImpl implements AmenityService {

	private final Logger log = LoggerFactory.getLogger(AmenityServiceImpl.class);

	@Inject
	private AmenityRepository amenityRepository;

	@Inject
    private UserRepository userRepository;
	/**
	 * Save a amenity.
	 * 
	 * @return the persisted entity
	 */
	public Amenity save(Amenity amenity) {
		log.debug("Request to save Amenity : {}", amenity);
		if (amenity.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			// user.setLogin(optional.get().getLogin());
			amenity.setCreate_by(user);
			log.info("Preshow user" + user);
		}
		Amenity result = amenityRepository.save(amenity);
		return result;
	}

	/**
	 * get all the amenitys.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Amenity> findAll(Pageable pageable) {
		log.debug("Request to get all Amenitys");
		Page<Amenity> result = amenityRepository.findAll(pageable);
		return result;
	}

	/**
	 * get one amenity by id.
	 * 
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Amenity findOne(Long id) {
		log.debug("Request to get Amenity : {}", id);
		Amenity amenity = amenityRepository.findOne(id);
		return amenity;
	}

	/**
	 * delete the amenity by id.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Amenity : {}", id);
		amenityRepository.delete(id);
	}
}
