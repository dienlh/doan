package com.hotel.app.repository;

import com.hotel.app.domain.Method_register;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Method_register entity.
 */
public interface Method_registerRepository extends JpaRepository<Method_register,Long> {

    @Query("select method_register from Method_register method_register where method_register.create_by.login = ?#{principal.username}")
    List<Method_register> findByCreate_byIsCurrentUser();

}
