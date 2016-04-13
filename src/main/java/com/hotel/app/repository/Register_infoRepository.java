package com.hotel.app.repository;

import com.hotel.app.domain.Register_info;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Register_info entity.
 */
public interface Register_infoRepository extends JpaRepository<Register_info, Long> {

	@Query("select register_info from Register_info register_info where register_info.create_by.login = ?#{principal.username}")
	List<Register_info> findByCreate_byIsCurrentUser();

	@Query("select register_info from Register_info register_info where register_info.last_modified_by.login = ?#{principal.username}")
	List<Register_info> findByLast_modified_byIsCurrentUser();

	@Query("select register_info from Register_info register_info "
			+ "where register_info.room.code like %?1% "
			+ "and register_info.customer.ic_passport_number like %?2% "
			+ "and (?3 =0L or register_info.method_payment.id = ?3) "
			+ "and (?4 =0L or register_info.status_payment.id = ?4) "
			+ "and (?5 =0L or register_info.method_register.id = ?5) "
			+ "and (?6 =0L or register_info.status_register.id = ?6) "
			+ "and register_info.date_checkin between ?7 and ?8")
	Page<Register_info> findAllByMultiAttr(Pageable pageable, String code, String ipnumber, Long method_payment,
			Long status_payment, Long method_register, Long status_register,LocalDate fromDate,LocalDate toDate);
	
	@Query("select register_info from Register_info register_info "
			+ "where register_info.room.code like %?1% "
			+ "and register_info.customer.ic_passport_number like %?2% "
			+ "and (?3 =0L or register_info.method_payment.id = ?3) "
			+ "and (?4 =0L or register_info.status_payment.id = ?4) "
			+ "and (?5 =0L or register_info.method_register.id = ?5) "
			+ "and (?6 =0L or register_info.status_register.id = ?6) "
			+ "and register_info.date_checkin between ?7 and ?8")
	List<Register_info> findAllByMultiAttr(String code, String ipnumber, Long method_payment,
			Long status_payment, Long method_register, Long status_register,LocalDate fromDate,LocalDate toDate);
	
	
	@Query("select register_info from Register_info register_info "
			+ "where register_info.room.code like %?1% "
			+ "and register_info.customer.ic_passport_number like %?2% "
			+ "and (?3 =0L or register_info.method_payment.id = ?3) "
			+ "and (?4 =0L or register_info.status_payment.id = ?4) "
			+ "and (?5 =0L or register_info.method_register.id = ?5) "
			+ "and (?6 =0L or register_info.status_register.id = ?6) ")
	Page<Register_info> findAllByMultiAttr(Pageable pageable, String code, String ipnumber, Long method_payment,
			Long status_payment, Long method_register, Long status_register);
	
	@Query("select register_info from Register_info register_info "
			+ "where register_info.room.code like %?1% "
			+ "and register_info.customer.ic_passport_number like %?2% "
			+ "and (?3 =0L or register_info.method_payment.id = ?3) "
			+ "and (?4 =0L or register_info.status_payment.id = ?4) "
			+ "and (?5 =0L or register_info.method_register.id = ?5) "
			+ "and (?6 =0L or register_info.status_register.id = ?6) ")
	List<Register_info> findAllByMultiAttr(String code, String ipnumber, Long method_payment,
			Long status_payment, Long method_register, Long status_register);
	
	@Query("select register_info from Register_info register_info "
			+ "where register_info.status_payment.id = 1L "
			+ "and register_info.status_register.id=1L")
	List<Register_info> findAllRegisterChecked();
}
