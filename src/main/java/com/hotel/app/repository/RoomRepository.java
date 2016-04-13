package com.hotel.app.repository;

import com.hotel.app.domain.Room;

import net.sf.cglib.core.Local;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Room entity.
 */
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("select room from Room room where room.create_by.login = ?#{principal.username}")
    List<Room> findByCreate_byIsCurrentUser();

    @Query("select room from Room room where room.last_modified_by.login = ?#{principal.username}")
    List<Room> findByLast_modified_byIsCurrentUser();

    @Query("select distinct room from Room room left join fetch room.imagess left join fetch room.amenitys")
    List<Room> findAllWithEagerRelationships();

    @Query("select room from Room room left join fetch room.imagess left join fetch room.amenitys where room.id =:id")
    Room findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select room from Room room where (?1=0L or room.type_room.id=?1) and (?2=0L or room.status_room=?2) and room.code like %?3%")
    Page<Room> findAllByTypeAndStatus(Pageable pageable, Long type_room,Long status_room,String code);

    @Query("select room from Room room where (?1=0L or room.type_room.id=?1) and (?2=0L or room.status_room=?2) and room.code like %?3%")
    List<Room> findAllByTypeAndStatus(Long type_room,Long status_room,String code);
    
    @Query("select room from Room room where room.code like ?1")
    Room findOneWithCode(String code);
    
    @Query("select room from Room room "
    		+ "where room.code like %?1% "
    		+ "and (?2=0L or room.type_room.id=?2) "
    		+ "and room.id not in( "
    		+ "select distinct register_info.room.id " 
    		+ "from Register_info register_info "
    		+ "where register_info.status_payment.id=1 "
    		+ "and register_info.status_register.id=1 "
    		+ "and ?4 >= date_checkout and ?3 < date_checkout "
    		+ "or ?4 > date_checkin and ?4 <= date_checkout)"
    		)
    Page<Room> findAllByRangerTimeAndMultiAttr(Pageable pageable,String code,Long type_room,LocalDate fromDate,LocalDate toDate);
    
    @Query("select room from Room room where room.id not in( "
    		+ "select distinct register_info.room.id " 
    		+ "from Register_info register_info "
    		+ "where register_info.status_payment.id=1 "
    		+ "and register_info.status_register.id=1 "
    		+ "and ?2 >= date_checkout and ?1 < date_checkout "
    		+ "or ?2 > date_checkin and ?2 <= date_checkout)"
    		)
    List<Room> findAllByRangerTime(LocalDate fromDate,LocalDate toDate);
    
    @Query("select distinct room from Room room left join fetch room.imagess left join fetch room.amenitys where "
    		+ "room.status_room.id not in (2L,7L,8L) "
    		+ "and (?3=0L or room.type_room.id=?3) "
    		+ "and room.id not in( "
    		+ "select distinct register_info.room.id " 
    		+ "from Register_info register_info "
    		+ "where register_info.status_payment.id=1L "
    		+ "and register_info.status_register.id=1L "
    		+ "and ?2 >= date_checkout and ?1 < date_checkout "
    		+ "or ?2 > date_checkin and ?2 <= date_checkout)"
    		)
    List<Room> findAllAvailable(LocalDate fromDate,LocalDate toDate,Long type_room);
    
    @Query("select room from Room room where "
    		+ "room.status_room.id not in (2L,7L,8L) "
    		+ "and room.id=?3 "
    		+ "and room.id not in( "
    		+ "select distinct register_info.room.id " 
    		+ "from Register_info register_info "
    		+ "where register_info.status_payment.id=1L "
    		+ "and register_info.status_register.id=1L "
    		+ "and ?2 >= date_checkout and ?1 < date_checkout "
    		+ "or ?2 > date_checkin and ?2 <= date_checkout)"
    		)
    Room findOneByRangerTime(LocalDate fromDate,LocalDate toDate,Long room);
}
