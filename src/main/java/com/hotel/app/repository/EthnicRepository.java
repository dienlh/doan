package com.hotel.app.repository;

import com.hotel.app.domain.Ethnic;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ethnic entity.
 */
public interface EthnicRepository extends JpaRepository<Ethnic,Long> {

}
