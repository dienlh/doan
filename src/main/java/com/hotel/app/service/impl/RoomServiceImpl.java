package com.hotel.app.service.impl;

import com.hotel.app.service.CurrencyService;
import com.hotel.app.service.RoomService;
import com.hotel.app.service.Status_roomService;
import com.hotel.app.service.Type_roomService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Currency;
import com.hotel.app.domain.Room;
import com.hotel.app.domain.Status_room;
import com.hotel.app.domain.Type_room;
import com.hotel.app.domain.User;
import com.hotel.app.repository.CurrencyRepository;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.repository.Status_roomRepository;
import com.hotel.app.repository.Type_roomRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
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

	@Inject
	private Type_roomService type_roomService;

	@Inject
	private Type_roomRepository type_roomRepository;

	@Inject
	private CurrencyService currencyService;

	@Inject
	private CurrencyRepository currencyRepository;

	@Inject
	private Status_roomService status_roomService;

	@Inject
	private Status_roomRepository status_roomRepository;

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
	public Page<Room> findAllByTypeAndStatus(Pageable pageable, Long type_room, Long status_room, String code) {
		Page<Room> page = roomRepository.findAllByTypeAndStatus(pageable, type_room, status_room, code);
		return page;
	}

	@Override
	public List<Room> findAllByTypeAndStatus(Long type_room, Long status_room, String code) {
		return roomRepository.findAllByTypeAndStatus(type_room, status_room, code);
	}

	@Override
	public Room findOneWithCode(String code) {
		return roomRepository.findOneWithCode(code);
	}

	@Override
	public Page<Room> findAllByRangerTimeAndMultiAttr(Pageable pageable, String code, Long type_room,
			LocalDate fromDate, LocalDate toDate) {
		return roomRepository.findAllByRangerTimeAndMultiAttr(pageable, code, type_room, fromDate, toDate);
	}

	@Override
	public List<Room> findAllByRangerTime(LocalDate fromDate, LocalDate toDate) {
		// TODO Auto-generated method stub
		return roomRepository.findAllByRangerTime(fromDate, toDate);
	}

	@Override
	public List<Room> findAllAvailable(LocalDate fromDate, LocalDate toDate, Long type_room) {
		return roomRepository.findAllAvailable(fromDate, toDate, type_room);
	}

	@Override
	public Room findOneByRangerTime(LocalDate fromDate, LocalDate toDate, Long room) {
		return roomRepository.findOneByRangerTime(fromDate, toDate, room);
	}

	@Override
	public String convertTypeDateImportExcel(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case Cell.CELL_TYPE_FORMULA:
			switch (cell.getCachedFormulaResultType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue().replaceAll("'", "");
			}
		}
		return null;
	}

	@Override
	public List<Room> importExcel(MultipartFile file) {
		String fileName = null;
		if (!file.isEmpty()) {
			try {
				fileName = file.getOriginalFilename();
				ZonedDateTime date = ZonedDateTime.now();

				Optional<ManagedUserDTO> optional = userRepository
						.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);

				User user = new User();
				user.setId(optional.get().getId());
				String fileSave = "E:/" + DateTime.now().getMillis() + fileName;

				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(fileSave)));
				buffStream.write(bytes);
				buffStream.close();
				List<Room> list = new ArrayList<Room>();
				log.debug("Upload file in " + fileSave);
				try {
					POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileSave));
					HSSFWorkbook wb = new HSSFWorkbook(fs);
					HSSFSheet sheet = wb.getSheetAt(0);
					Iterator<Row> iterator = sheet.iterator();

					while (iterator.hasNext()) {
						Row nextRow = iterator.next();
						if (nextRow.getRowNum() == 0) {
							continue;
						}
						Iterator<Cell> cellIterator = nextRow.cellIterator();
						Room room = new Room();
						Boolean checkAvailable = false;
						while (cellIterator.hasNext()) {
							if (checkAvailable == true) {
								break;
							}
							Cell cell = cellIterator.next();
							int index = cell.getColumnIndex();
							String data = convertTypeDateImportExcel(cell);
							switch (index) {
							case 1:
								if (findOneWithCode(data) == null) {
									room.setCode(data);
								} else {
									checkAvailable = true;
								}
								break;
							case 2:
								room.setKey_code(data);
								break;
							case 3:
								room.setTitle((data));
								break;
							case 4:
								room.setIs_pet(Boolean.valueOf(data));
								break;
							case 5:
								room.setIs_bed_kid(Boolean.valueOf(data));
								break;
							case 6:
								room.setNumber_of_livingroom(Double.valueOf(data).intValue());
								break;
							case 7:
								room.setNumber_of_bedroom(Double.valueOf(data).intValue());
								break;
							case 8:
								room.setNumber_of_toilet(Double.valueOf(data).intValue());
								break;
							case 9:
								room.setNumber_of_kitchen(Double.valueOf(data).intValue());
								break;
							case 10:
								room.setNumber_of_bathroom(Double.valueOf(data).intValue());
								break;
							case 11:
								room.setFloor(data);
								break;
							case 12:
								room.setOrientation(data);
								break;
							case 13:
								room.setSurface_size(data);
								break;
							case 14:
								room.setMax_adults(Double.valueOf(data).intValue());
								break;
							case 15:
								room.setMax_kids(Double.valueOf(data).intValue());
								break;
							case 16:
								room.setHourly_price(BigDecimal.valueOf(Double.valueOf(data)));
								break;
							case 17:
								room.setDaily_price(BigDecimal.valueOf(Double.valueOf(data)));
								break;
							case 18:
								room.setMonthly_price(BigDecimal.valueOf(Double.valueOf(data)));
								break;
							case 19:
								Type_room type_room = type_roomService.findByName(data);
								if (type_room == null) {
									type_room = new Type_room();
									type_room.setName(data);
									type_room = type_roomRepository.save(type_room);
								}
								room.setType_room(type_room);
								break;
							case 20:
								Currency currency = currencyService.findByCode(data);
								if (currency == null) {
									currency = new Currency();
									currency.setCode(data);
									currency = currencyRepository.save(currency);
								}
								room.setCurrency(currency);
								break;
							case 21:
								Status_room status_room = status_roomService.findByName(data);
								if (status_room == null) {
									status_room = new Status_room();
									status_room.setName(data);
									status_room = status_roomRepository.save(status_room);
								}
								room.setStatus_room(status_room);
								break;
							default:
								break;
							}
						}
						if (checkAvailable == true) {
							continue;
						} else {
							room.setCreate_by(user);
							list.add(room);
						}
					}
					wb.close();
					log.debug(list + "");
					roomRepository.save(list);
				} catch (Exception ioe) {
					ioe.printStackTrace();
				}
				return list;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
