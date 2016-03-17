package com.hotel.app.service;

import com.hotel.app.domain.Register_info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Interface for managing Register_info.
 */
public interface Register_infoService {

    /**
     * Save a register_info.
     * @return the persisted entity
     */
    public Register_info save(Register_info register_info);

    /**
     *  get all the register_infos.
     *  @return the list of entities
     */
    public Page<Register_info> findAll(Pageable pageable);
    /**
     *  get all the register_infos where Reservation is null.
     *  @return the list of entities
     */
    public List<Register_info> findAllWhereReservationIsNull();

    /**
     *  get the "id" register_info.
     *  @return the entity
     */
    public Register_info findOne(Long id);

    /**
     *  delete the "id" register_info.
     */
    public void delete(Long id);
    
    public Page<Register_info> findAllByMultiAttr(Pageable pageable, String code, String ipnumber, Long method_payment,
			Long status_payment, Long method_register, Long status_register,LocalDate fromDate,LocalDate toDate);
    
    public Page<Register_info> findAllByMultiAttr(Pageable pageable, String code, String ipnumber, Long method_payment,
			Long status_payment, Long method_register, Long status_register);
    
    public List<Register_info> findAllRegisterChecked();
}
