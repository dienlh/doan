package com.hotel.app.service.impl;

import com.hotel.app.service.RoomService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Room;
import com.hotel.app.domain.User;
import com.hotel.app.repository.RoomRepository;
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
 * Service Implementation for managing Room.
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

	private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Inject
	private RoomRepository roomRepository;
	
	@Inject
	private UserRepository userRepository;

	/**
	 * Save a room.
	 * 
	 * @return the persisted entity
	 */
	public Room save(Room room) {
		log.debug("Request to save Room : {}", room);
		if (room.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			room.setCreate_by(user);
			log.info("Preshow user" + user);
		} else {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);
			User user = new User();
			user.setId(optional.get().getId());
			// user.setLogin(optional.get().getLogin());
			room.setLast_modified_by(user);
			room.setLast_modified_date(ZonedDateTime.now());
		}
		Room result = roomRepository.save(room);
		return result;
	}

	/**
	 * get all the rooms.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Room> findAll(Pageable pageable) {
		log.debug("Request to get all Rooms");
		Page<Room> result = roomRepository.findAll(pageable);
		return result;
	}

	/**
	 * get one room by id.
	 * 
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Room findOne(Long id) {
		log.debug("Request to get Room : {}", id);
		Room room = roomRepository.findOneWithEagerRelationships(id);
		return room;
	}

	/**
	 * delete the room by id.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Room : {}", id);
		roomRepository.delete(id);
	}
	@Override
	public Page<Room> findAllByTypeAndStatus(Pageable pageable, Long type_room, Long status_room,String code) {
		Page<Room> page = roomRepository.findAllByTypeAndStatus(pageable, type_room, status_room,code);
		return page;
	}
	
	@Override
	public Room findOneWithCode(String code) {
		return roomRepository.findOneWithCode(code);
	}
	
	@Override
	public Page<Room> findAllByRangerTimeAndMultiAttr(Pageable pageable, String code ,Long type_room,LocalDate fromDate,LocalDate toDate) {
		return roomRepository.findAllByRangerTimeAndMultiAttr(pageable, code, type_room, fromDate, toDate);
	}
	
	@Override
	public List<Room> findAllByRangerTime(LocalDate fromDate, LocalDate toDate) {
		// TODO Auto-generated method stub
		return roomRepository.findAllByRangerTime(fromDate, toDate);
	}
}
