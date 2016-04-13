package com.hotel.app.repository;

import com.hotel.app.domain.Bill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Bill entity.
 */
public interface BillRepository extends JpaRepository<Bill, Long> {

	@Query("select bill from Bill bill where bill.create_by.login = ?#{principal.username}")
	List<Bill> findByCreate_byIsCurrentUser();

	@Query("select bill from Bill bill where bill.reservation.id=?1")
	Bill findOneByReservationId(Long reservationID);

	@Query("select bill from Bill bill where "
			+ "(?1 = 0L or bill.reservation.register_info.room.id = ?1) "
			+ "and (?2 = 0L or bill.customer.id = ?2) "
			+ "and (?3 = 0L or bill.method_payment.id = ?3) "
			+ "and (?4 = 0L or bill.status_payment.id = ?4) "
			+ "and (?5 = 0L or bill.reservation.register_info.method_register.id = ?5) "
			+ "and (?6 = 0L or bill.status_bill.id = ?6) "
			)
    Page<Bill> findAllByMultiAttr(Pageable pageable, Long room, Long customer, Long method_payment,
			Long status_payment, Long method_register, Long status_bill);
	@Query("select bill from Bill bill where "
			+ "(?1 = 0L or bill.reservation.register_info.room.id = ?1) "
			+ "and (?2 = 0L or bill.customer.id = ?2) "
			+ "and (?3 = 0L or bill.method_payment.id = ?3) "
			+ "and (?4 = 0L or bill.status_payment.id = ?4) "
			+ "and (?5 = 0L or bill.reservation.register_info.method_register.id = ?5) "
			+ "and (?6 = 0L or bill.status_bill.id = ?6) "
			)
	List<Bill> findAllByMultiAttr(Long room, Long customer, Long method_payment, Long status_payment,
			Long method_register, Long status_bill);

	@Query("select bill from Bill bill where "
			+ "(?1 = 0L or bill.reservation.register_info.room.id = ?1) "
			+ "and (?2 = 0L or bill.customer.id = ?2) "
			+ "and (?3 = 0L or bill.method_payment.id = ?3) "
			+ "and (?4 = 0L or bill.status_payment.id = ?4) "
			+ "and (?5 = 0L or bill.reservation.register_info.method_register.id = ?5) "
			+ "and (?6 = 0L or bill.status_bill.id = ?6) "
			+ "and bill.create_date between ?7 and ?8 "
			)
	Page<Bill> findAllByMultiAttr(Pageable pageable, Long room, Long customer, Long method_payment, Long status_payment,
			Long method_register, Long status_bill, ZonedDateTime fromDate, ZonedDateTime toDate);
	
	@Query("select bill from Bill bill where "
			+ "(?1 = 0L or bill.reservation.register_info.room.id = ?1) "
			+ "and (?2 = 0L or bill.customer.id = ?2) "
			+ "and (?3 = 0L or bill.method_payment.id = ?3) "
			+ "and (?4 = 0L or bill.status_payment.id = ?4) "
			+ "and (?5 = 0L or bill.reservation.register_info.method_register.id = ?5) "
			+ "and (?6 = 0L or bill.status_bill.id = ?6) "
			+ "and bill.create_date between ?7 and ?8 "
			)
	List<Bill> findAllByMultiAttr(Long room, Long customer, Long method_payment, Long status_payment,
			Long method_register, Long status_bill, ZonedDateTime fromDate, ZonedDateTime toDate);
}
