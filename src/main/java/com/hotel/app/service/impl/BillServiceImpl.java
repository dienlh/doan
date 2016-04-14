package com.hotel.app.service.impl;

import com.hotel.app.service.BillService;
import com.hotel.app.service.Bill_serviceService;
import com.hotel.app.service.ReservationService;
import com.hotel.app.web.rest.dto.ManagedUserDTO;
import com.hotel.app.domain.Bill;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.domain.Method_payment;
import com.hotel.app.domain.Reservation;
import com.hotel.app.domain.Status_bill;
import com.hotel.app.domain.Status_payment;
import com.hotel.app.domain.User;
import com.hotel.app.repository.BillRepository;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.repository.UserRepository;
import com.hotel.app.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Bill.
 */
@Service
@Transactional
public class BillServiceImpl implements BillService {

	private final Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

	@Inject
	private BillRepository billRepository;

	@Inject
	private UserRepository userRepository;

	@Inject
	private ReservationService reservationService;

	@Inject
	private Bill_serviceService bill_serviceService;

	/**
	 * Save a bill.
	 * 
	 * @return the persisted entity
	 */
	public Bill save(Bill bill) {
		log.debug("Request to save Bill : {}", bill);
		if (bill.getId() == null) {
			Optional<ManagedUserDTO> optional = userRepository
					.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).map(ManagedUserDTO::new);

			User user = new User();
			user.setId(optional.get().getId());
			user.setLogin(optional.get().getLogin());
			bill.setCreate_by(user);
			log.info("Preshow user" + user);
		} else {
			Status_bill status_bill = new Status_bill();
			status_bill.setId(2L);
			bill.setStatus_bill(status_bill);
		}
		Bill result = billRepository.save(bill);
		return result;
	}

	/**
	 * get all the bills.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Bill> findAll(Pageable pageable) {
		log.debug("Request to get all Bills");
		Page<Bill> result = billRepository.findAll(pageable);
		return result;
	}

	/**
	 * get one bill by id.
	 * 
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Bill findOne(Long id) {
		log.debug("Request to get Bill : {}", id);
		Bill bill = billRepository.findOne(id);
		return bill;
	}

	/**
	 * delete the bill by id.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Bill : {}", id);
		billRepository.delete(id);
	}

	@Override
	public Bill findOneByReservationId(Long reservationID) {
		return billRepository.findOneByReservationId(reservationID);
	}

	@Override
	public BigDecimal computingFeesTotal(BigDecimal feesroom, BigDecimal feesservice, BigDecimal feesother,
			BigDecimal feesbonus, BigDecimal deposit) {
		return BigDecimal.valueOf(feesroom.doubleValue() + feesservice.doubleValue() + feesbonus.doubleValue()
				+ feesother.doubleValue() - deposit.doubleValue());
	}

	@Override
	public BigDecimal computingFeesRoomByReservationId(Long reservationId) {
		Reservation reservation = reservationService.findOne(reservationId);
		Long totalSeconds = reservation.getTime_checkout().getLong(ChronoField.INSTANT_SECONDS)
				- reservation.getTime_checkin().getLong(ChronoField.INSTANT_SECONDS);
		double feesRoom = 0;

		if (reservation.getRegister_info().getDate_checkout()
				.equals(reservation.getRegister_info().getDate_checkin())) {
			feesRoom = totalSeconds / (60 * 60)
					* reservation.getRegister_info().getRoom().getHourly_price().doubleValue();
		} else {
			double totalDay = (double) totalSeconds / (24 * 60 * 60);
			if (totalDay < 30) {
				feesRoom = totalDay * reservation.getRegister_info().getRoom().getDaily_price().doubleValue();
			} else {
				feesRoom = (double) totalDay / 30
						* reservation.getRegister_info().getRoom().getMonthly_price().doubleValue();
			}
		}
		return BigDecimal.valueOf(feesRoom);
	}

	@Override
	public BigDecimal computingFeesServiceByIdReservation(Long reservationId) {
		List<Bill_service> bill_services = bill_serviceService.findAllByReservationId(reservationId);
		double total = 0;
		for (Bill_service bill_service : bill_services) {
			total += bill_service.getTotal().doubleValue();
		}
		return BigDecimal.valueOf(total);
	}

	@Override
	public Bill createByReservationId(Long reservationId) {
		Reservation reservation = reservationService.findOne(reservationId);
		Bill bill = new Bill();
		try {
			bill.setFees_bonus(BigDecimal.ZERO);
			bill.setFees_other(BigDecimal.ZERO);
			bill.setFees_room(computingFeesRoomByReservationId(reservationId).setScale(0, BigDecimal.ROUND_HALF_DOWN));
			bill.setFees_service(
					computingFeesServiceByIdReservation(reservationId).setScale(0, BigDecimal.ROUND_HALF_DOWN));

			bill.setTotal(computingFeesTotal(bill.getFees_room(), bill.getFees_service(), bill.getFees_bonus(),
					bill.getFees_other(), reservation.getRegister_info().getDeposit_value()).setScale(0,
							BigDecimal.ROUND_HALF_DOWN));

			bill.setFees_vat(BigDecimal.valueOf(bill.getTotal().doubleValue() * 10 / 100).setScale(0,
					BigDecimal.ROUND_HALF_DOWN));
			bill.setTotal_vat(BigDecimal.valueOf(bill.getTotal().doubleValue() + bill.getFees_vat().doubleValue())
					.setScale(0, BigDecimal.ROUND_HALF_DOWN));

//			bill.setCurrency(reservation.getRegister_info().getRoom().getCurrency());

			Method_payment method_payment = new Method_payment();
			method_payment.setId(1L);
			bill.setMethod_payment(method_payment);

			Status_payment status_payment = new Status_payment();
			status_payment.setId(2L);
			bill.setStatus_payment(status_payment);

			Status_bill status_bill = new Status_bill();
			status_bill.setId(1L);
			bill.setStatus_bill(status_bill);

			bill.setCurrency(reservation.getRegister_info().getCurrency());
			bill.setCustomer(reservation.getRegister_info().getCustomer());

			bill.setReservation(reservation);

		} catch (Exception e) {
			return null;
		}
		return bill;
	}

	@Override
	public List<Bill> findAllByMultiAttr(Long room, Long customer, Long method_payment, Long status_payment,
			Long method_register, Long status_bill) {
		// TODO Auto-generated method stub
		return billRepository.findAllByMultiAttr(room, customer, method_payment, status_payment, method_register,
				status_bill);
	}

	@Override
	public List<Bill> findAllByMultiAttr(Long room, Long customer, Long method_payment, Long status_payment,
			Long method_register, Long status_bill, ZonedDateTime fromDate, ZonedDateTime toDate) {
		// TODO Auto-generated method stub
		return billRepository.findAllByMultiAttr(room, customer, method_payment, status_payment, method_register,
				status_bill, fromDate, toDate);
	}

	@Override
	public Page<Bill> findAllByMultiAttr(Pageable pageable, Long room, Long customer, Long method_payment,
			Long status_payment, Long method_register, Long status_bill) {
		// TODO Auto-generated method stub
		return billRepository.findAllByMultiAttr(pageable, room, customer, method_payment, status_payment, method_register, status_bill);
	}

	@Override
	public Page<Bill> findAllByMultiAttr(Pageable pageable, Long room, Long customer, Long method_payment,
			Long status_payment, Long method_register, Long status_bill, ZonedDateTime fromDate, ZonedDateTime toDate) {
		// TODO Auto-generated method stub
		return billRepository.findAllByMultiAttr(pageable, room, customer, method_payment, status_payment, method_register, status_bill, fromDate, toDate);
	}
}
