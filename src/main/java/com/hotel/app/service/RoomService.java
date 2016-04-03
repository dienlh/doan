package com.hotel.app.service;

import com.hotel.app.domain.Room;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Interface for managing Room.
 */
public interface RoomService {

    /**
     * Save a room.
     * @return the persisted entity
     */
    public Room save(Room room);

    /**
     *  get all the rooms.
     *  @return the list of entities
     */
    public Page<Room> findAll(Pageable pageable);

    /**
     *  get the "id" room.
     *  @return the entity
     */
    public Room findOne(Long id);

    /**
     *  delete the "id" room.
     */
    public void delete(Long id);
    
    public Page<Room> findAllByTypeAndStatus(Pageable pageable, Long type_room,Long status_room,String code);
    
    public List<Room> findAllByTypeAndStatus(Long type_room,Long status_room,String code);
    
    public Room findOneWithCode(String code);
    
    public Page<Room> findAllByRangerTimeAndMultiAttr(Pageable pageable, String code ,Long type_room,LocalDate fromDate,LocalDate toDate);

    public List<Room> findAllByRangerTime(LocalDate fromDate,LocalDate toDate);
    
    public List<Room> findAllAvailable(LocalDate fromDate,LocalDate toDate,Long type_room);
    
    public Room findOneByRangerTime(LocalDate fromDate,LocalDate toDate,Long room);
    
    public List<Room> importExcel(MultipartFile multipartFile);
    
    public String convertTypeDateImportExcel(Cell cell);
}
