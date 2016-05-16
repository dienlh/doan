package com.hotel.app.service.impl;

import com.hotel.app.service.Bill_serviceService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Bill;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.domain.Status_bill_service;
import com.hotel.app.domain.User;
import com.hotel.app.repository.Bill_serviceRepository;
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
 * Service Implementation for managing Bill_service.
 */
@Service
@Transactional
public class Bill_serviceServiceImpl implements Bill_serviceService {

	private final Logger log = LoggerFactory.getLogger(Bill_serviceServiceImpl.class);

	@Inject
	private Bill_serviceRepository bill_serviceRepository;

	@Inject
	private UserRepository userRepository;

	/**
	 * Save a bill_service.
	 * 
	 * @return the persisted entity
	 */
	public Bill_service save(Bill_service bill_service) {
		log.debug("Request to save Bill_service : {}", bill_service);
		if (bill_service.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);

			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			bill_service.setCreate_by(user);
			log.info("Preshow user" + user);
			Status_bill_service status_bill_service = new Status_bill_service();
			status_bill_service.setId(3L);
			bill_service.setStatus_bill_service(status_bill_service);
		}
		Bill_service result = bill_serviceRepository.save(bill_service);
		return result;
	}

	/**
	 * get all the bill_services.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Bill_service> findAll(Pageable pageable) {
		log.debug("Request to get all Bill_services");
		Page<Bill_service> result = bill_serviceRepository.findAll(pageable);
		return result;
	}

	/**
	 * get one bill_service by id.
	 * 
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Bill_service findOne(Long id) {
		log.debug("Request to get Bill_service : {}", id);
		Bill_service bill_service = bill_serviceRepository.findOne(id);
		return bill_service;
	}

	/**
	 * delete the bill_service by id.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Bill_service : {}", id);
		bill_serviceRepository.delete(id);
	}

	@Override
	public Page<Bill_service> findAllByMultiAttr(Pageable pageable, Long serviceId, Long statusId, Long reservationId,
			ZonedDateTime fromDate, ZonedDateTime toDate) {
		return bill_serviceRepository.findAllByMultiAttr(pageable, serviceId, statusId, reservationId, fromDate, toDate);
	}

	@Override
	public List<Bill_service> findAllByMultiAttr(Long serviceId, Long statusId, Long reservationId,
			ZonedDateTime fromDate, ZonedDateTime toDate) {
		return bill_serviceRepository.findAllByMultiAttr(serviceId, statusId, reservationId, fromDate, toDate);
	}
	
	@Override
	public Page<Bill_service> findAllByReservationId(Pageable pageable, Long reservationId) {
		return bill_serviceRepository.findAllByReservationId(pageable, reservationId);
	}
	
	@Override
	public List<Bill_service> findAllByReservationId(Long reservationId) {
		return bill_serviceRepository.findAllByReservationId(reservationId);
	}
	
	@Override
	public List<Bill_service> findAllByListId(List<Long> listId) {
		return bill_serviceRepository.findAllByListId(listId);
	}
	@Override
	public List<Bill_service> findAllByReservationIdAndStatus(Long reservationId, Long statusId) {
		return bill_serviceRepository.findAllByReservationIdAndStatus(reservationId,statusId);
	}
}
