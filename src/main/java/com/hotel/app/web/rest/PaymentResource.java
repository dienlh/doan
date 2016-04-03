package com.hotel.app.web.rest;

import com.hotel.app.domain.*;
import com.hotel.app.repository.CustomerRepository;
import com.hotel.app.repository.Register_infoRepository;
import com.hotel.app.service.Register_infoService;
import com.hotel.app.service.CustomerService;
import com.hotel.app.service.Method_paymentService;
import com.hotel.app.service.MypayPayment;
import com.hotel.app.service.RoomService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.lang.String;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.*;

import java.io.IOException;
import java.time.LocalDate;

import org.joda.time.DateTime;
import org.json.JSONException;

/**
 * REST controller for managing users.
 *
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of
 * authorities.
 * </p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between User and Authority, and send everything to the client side: there
 * would be no DTO, a lot less code, and an outer-join which would be good for
 * performance.
 * </p>
 * <p>
 * We use a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities,
 * because people will quite often do relationships with the user, and we don't
 * want them to get the authorities all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our users' application
 * because of this use-case.</li>
 * <li>Not having an outer join causes n+1 requests to the database. This is not
 * a real issue as we have by default a second-level cache. This means on the
 * first HTTP call we do the n+1 requests, but then all authorities come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li>As this manages users, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </p>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

	private final Logger log = LoggerFactory.getLogger(PaymentResource.class);
	private final String URL_PATH = "http://localhost:8080/hotel";
	// private final String URL_PATH="http://hotel.com.vn";

	@Inject
	private RoomService roomService;

	@Inject
	private CustomerService customerService;

	@Inject
	private Method_paymentService method_paymentService;

	@Inject
	private CustomerRepository customerRepository;

	@Inject
	private Register_infoRepository register_infoRepository;

	@Inject
	private Register_infoService register_infoService;

	@RequestMapping(value = "/returnSuccess", method = RequestMethod.GET)
	public RedirectView returnSuccess(@RequestParam(value = "tranid", defaultValue = "tranid") String tranid,
			@RequestParam(value = "accountId", defaultValue = "accountId") String accountId,
			@RequestParam(value = "secureSecret", defaultValue = "secureSecret") String secureSecret,
			@RequestParam(value = "orderId", defaultValue = "orderId") String orderId) {

		Register_info register_info = register_infoService.findOne(Long.parseLong(orderId.split("-")[0]));
		Status_payment status_payment = new Status_payment();
		status_payment.setId(1L);
		register_info.setStatus_payment(status_payment);

		Status_register status_register = new Status_register();
		status_register.setId(1L);
		register_info.setStatus_register(status_register);

		User user = new User();
		user.setId(1L);
		register_info.setLast_modified_by(user);
		register_info.setLast_modified_date(ZonedDateTime.now());
		register_infoRepository.save(register_info);

		String url = URL_PATH + "/#/hotel/resultRegister/" + orderId.split("-")[0];
		return new RedirectView(url);
	}

	@RequestMapping(value = "/returnFail", method = RequestMethod.GET)
	public RedirectView returnFail(@RequestParam(value = "orderId", defaultValue = "orderId") String orderId) {
		Register_info register_info = register_infoService.findOne(Long.parseLong(orderId));
		Status_register status_register = new Status_register();
		status_register.setId(2L);
		register_info.setStatus_register(status_register);

		User user = new User();
		user.setId(1L);
		register_info.setLast_modified_by(user);
		register_info.setLast_modified_date(ZonedDateTime.now());
		register_infoRepository.save(register_info);

		String url = URL_PATH + "/#/hotel/resultRegister/" + orderId;
		return new RedirectView(url);
	}

	@RequestMapping(value = "/redirectionRegister", method = RequestMethod.POST)
	public RedirectView redirectionRegister(
			@RequestParam(value = "firstname", defaultValue = "firstname") String firstname,
			@RequestParam(value = "lastname", defaultValue = "lastname") String lastname,
			@RequestParam(value = "email", defaultValue = "email") String email,
			@RequestParam(value = "phone", defaultValue = "phone") String phone,
			@RequestParam(value = "todate") String todate, @RequestParam(value = "enddate") String enddate,
			@RequestParam(value = "social_security", defaultValue = "social_security") String social_security,
			@RequestParam(value = "address", defaultValue = "address") String address,
			@RequestParam(value = "description", defaultValue = "description") String description1,
			@RequestParam(value = "quantity", defaultValue = "quantity") String quantity,
			@RequestParam(value = "method", defaultValue = "method") String method,
			@RequestParam(value = "paymentValue", defaultValue = "paymentValue") String paymentValue,
			@RequestParam(value = "methodBooking", defaultValue = "methodBooking") String methodBooking,
			@RequestParam(value = "id_room", defaultValue = "id_room") String id_room)
			throws IOException, JSONException, ParseException {
		// org.bson.types.ObjectId ob=new org.bson.types.ObjectId();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date checkin = simpleDateFormat.parse(todate);
		Date checkout = simpleDateFormat.parse(enddate);

		System.out.println(simpleDateFormat.format(checkin));
		System.out.println(simpleDateFormat.format(checkout));

		if (checkin.compareTo(checkout) > 0) {
			return new RedirectView(
					URL_PATH + "/#/template/book?idRoom=" + id_room + "&checkin=" + simpleDateFormat.format(checkin)
							+ "&checkout=" + simpleDateFormat.format(checkout) + "&message=faildeddate");
		}
		if (roomService.findOneByRangerTime(LocalDate.parse(simpleDateFormat.format(checkin)),
				LocalDate.parse(simpleDateFormat.format(checkout)), Long.parseLong(id_room)) == null) {
			return new RedirectView(
					URL_PATH + "/#/template/book?idRoom=" + id_room + "&checkin=" + simpleDateFormat.format(checkin)
							+ "&checkout=" + simpleDateFormat.format(checkout) + "&message=unavailable");
		}

		Room room = roomService.findOne(Long.parseLong(id_room));
		Customer customerInfo = customerService.findByIcPassPortNumber(social_security);
		if (customerInfo == null) {
			Customer customer = new Customer();
			customer.setFull_name(firstname + " " + lastname);
			customer.setIc_passport_number(social_security);
			customer.setEmail(email);
			customer.setPhone_number(phone);
			User user = new User();
			user.setId(1L);
			customer.setCreate_by(user);
			customer.setCreate_date(java.time.ZonedDateTime.now());
			customerInfo = customerRepository.save(customer);
		}

		Register_info register_info = new Register_info();
		register_info.setDate_checkin(LocalDate.parse(simpleDateFormat.format(checkin)));
		register_info.setDate_checkout(LocalDate.parse(simpleDateFormat.format(checkin)));
		register_info.setRoom(room);
		int totalDay = Math.round((simpleDateFormat.parse(enddate + " 00:00:00").getTime()
				- simpleDateFormat.parse(todate + " 00:00:00").getTime()) / (24 * 60 * 60 * 1000));
		log.info("Ngay" + totalDay);
		if (totalDay == 0) {
			totalDay++;
		}
		if (totalDay < 30) {
			register_info.setDeposit_value(BigDecimal.valueOf(room.getDaily_price().doubleValue() * totalDay * 0.25)
					.setScale(0, RoundingMode.HALF_DOWN));
		} else {
			register_info.setDeposit_value(BigDecimal.valueOf(room.getMonthly_price().doubleValue() * totalDay * 0.25)
					.setScale(0, RoundingMode.HALF_DOWN));
		}
		register_info.setNumber_of_adult(0);
		register_info.setNumber_of_kid(0);
		register_info.setCustomer(customerInfo);
		register_info.setCurrency(room.getCurrency());

		Method_payment method_payment = method_paymentService.createPaymentOnline();
		register_info.setMethod_payment(method_payment);

		Status_payment status_payment = new Status_payment();
		status_payment.setId(2L);
		register_info.setStatus_payment(status_payment);

		Method_register method_register = new Method_register();
		method_register.setId(1L);
		register_info.setMethod_register(method_register);

		Status_register status_register = new Status_register();
		status_register.setId(3L);
		register_info.setStatus_register(status_register);

		User user = new User();
		user.setId(1L);
		register_info.setCreate_by(user);
		register_info.setCreate_date(java.time.ZonedDateTime.now());

		if (method.equals("Thanh toán sau")) {
			status_register = new Status_register();
			status_register.setId(1L);
			register_info.setStatus_register(status_register);
			Register_info info = register_infoRepository.save(register_info);
			return new RedirectView("/hotel/#/template/resultRegister/" + info.getId());
		}
		Register_info info = register_infoRepository.save(register_info);
		String orderId = info.getId().toString() + "-" + ZonedDateTime.now().getLong(ChronoField.INSTANT_SECONDS);
		String amount = info.getDeposit_value().toString();
		String urlSuccess = URL_PATH + "/api/returnSuccess";
		String urlFail = URL_PATH + "/api/returnFail";
		String sandbox = "true";
		String description = "Thanh toan hoa don tren hotel.com.vn";
		MypayPayment payment1 = new MypayPayment(orderId, description, urlSuccess, urlFail, amount,
				Boolean.valueOf(sandbox));
		String url = payment1.sendRedirectURL();
		return new RedirectView(url);
	}

}
