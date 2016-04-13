package com.hotel.app.service.impl;

import com.hotel.app.service.ProfileService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Profile;
import com.hotel.app.domain.User;
import com.hotel.app.repository.ProfileRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Profile.
 */
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

	@Inject
	private ProfileRepository profileRepository;

	@Inject
	private UserRepository userRepository;

	/**
	 * Save a profile.
	 * 
	 * @return the persisted entity
	 */
	public Profile save(Profile profile) {
		log.debug("Request to save Profile : {}", profile);
		if (profile.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);

			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			profile.setCreate_by(user);
			log.info("Preshow user" + user);
		} else {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			// user.setLogin(optional.get().getLogin());
			profile.setLast_modified_by(user);
			profile.setLast_modified_date(ZonedDateTime.now());
			if(profile.getStatus_profile().getId()==2L || profile.getStatus_profile().getId()==3L){
				profile.setLeave_date(LocalDate.now());
			}
		}
		Profile result = profileRepository.save(profile);
		return result;
	}

	/**
	 * get all the profiles.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Profile> findAll(Pageable pageable) {
		log.debug("Request to get all Profiles");
		Page<Profile> result = profileRepository.findAll(pageable);
		return result;
	}

	/**
	 * get one profile by id.
	 * 
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Profile findOne(Long id) {
		log.debug("Request to get Profile : {}", id);
		Profile profile = profileRepository.findOne(id);
		return profile;
	}

	/**
	 * delete the profile by id.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Profile : {}", id);
		profileRepository.delete(id);
	}

	public Page<Profile> findByMultiAttr(Pageable pageable, Long positionId, Long departmentId, Long statusId,
			String full_name) {
		log.debug("Request to fidn by multi att Profile : {}", positionId + departmentId + statusId);
		return profileRepository.findByMultiAttr(pageable, positionId, departmentId, statusId, full_name);
	}

	public Page<Profile> findByMultiAttrWithRanger(Pageable pageable, Long positionId, Long departmentId, Long statusId,
			String full_name, LocalDate fromDate, LocalDate toDate) {
		log.debug("Request to fidn by multi att Profile : {}", positionId + departmentId + statusId);
		return profileRepository.findByMultiAttrWithRanger(pageable, positionId, departmentId, statusId, full_name,
				fromDate,toDate);
	}
	
	@Override
	public List<Profile> findByMultiAttrWithRanger(Long positionId, Long departmentId, Long statusId, String full_name,
			LocalDate fromDate, LocalDate toDate) {
		// TODO Auto-generated method stub
		return profileRepository.findByMultiAttrWithRanger( positionId, departmentId, statusId, full_name,
				fromDate,toDate);
	}

}
