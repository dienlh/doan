package com.hotel.app.service;

import com.hotel.app.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing Bill.
 */
public interface BillService {

	/**
	 * Save a bill.
	 * 
	 * @return the persisted entity
	 */
	public Bill save(Bill bill);

	/**
	 * get all the bills.
	 * 
	 * @return the list of entities
	 */
	public Page<Bill> findAll(Pageable pageable);

	/**
	 * get the "id" bill.
	 * 
	 * @return the entity
	 */
	public Bill findOne(Long id);

	/**
	 * delete the "id" bill.
	 */
	public void delete(Long id);

	public Bill findOneByReservationId(Long reservationID);

	public Bill createByReservationId(Long reservationId);

	public BigDecimal computingFeesTotal(BigDecimal feesroom, BigDecimal feesservice, BigDecimal feesother,
			BigDecimal feesbonus, BigDecimal deposit);

	public BigDecimal computingFeesRoomByReservationId(Long reservationId);

	public BigDecimal computingFeesServiceByIdReservation(Long reservationId);

	public Page<Bill> findAllByMultiAttr(Pageable pageable, Long room, Long customer, Long method_payment,
			Long status_payment, Long method_register, Long status_bill);

	public List<Bill> findAllByMultiAttr(Long room, Long customer, Long method_payment, Long status_payment,
			Long method_register, Long status_bill);

	public Page<Bill> findAllByMultiAttr(Pageable pageable, Long room, Long customer, Long method_payment,
			Long status_payment, Long method_register, Long status_bill,ZonedDateTime fromDate,ZonedDateTime toDate);

	public List<Bill> findAllByMultiAttr(Long room, Long customer, Long method_payment, Long status_payment,
			Long method_register, Long status_bill,ZonedDateTime fromDate,ZonedDateTime toDate);
}
