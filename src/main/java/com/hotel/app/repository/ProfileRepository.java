package com.hotel.app.repository;

import com.hotel.app.domain.Employee;
import com.hotel.app.domain.Profile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Profile entity.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	@Query("select profile from Profile profile where profile.create_by.login = ?#{principal.username}")
	List<Profile> findByCreate_byIsCurrentUser();

	@Query("select profile from Profile profile where profile.last_modified_by.login = ?#{principal.username}")
	List<Profile> findByLast_modified_byIsCurrentUser();

	@Query("select profile " + "from Profile profile " + "where ( ?1 =0L or profile.position.id=?1)"
			+ "and (?2 =0L or profile.department.id=?2)" + "and (?3 =0L or profile.status_profile.id=?3)"
			+ "and profile.employee.full_name like %?4%")
	Page<Profile> findByMultiAttr(Pageable pageable, Long positionId, Long departmentId, Long statusId,
			String full_name);

	@Query("select profile " + "from Profile profile " + "where ( ?1 =0L or profile.position.id=?1)"
			+ "and (?2 =0L or profile.department.id=?2)" + "and (?3 =0L or profile.status_profile.id=?3)"
			+ "and (?4='' or ?4 is null or profile.employee.full_name like %?4%)"
			+ "and profile.join_date between ?5 and ?6")
	Page<Profile> findByMultiAttrWithRanger(Pageable pageable, Long positionId, Long departmentId, Long statusId,
			String full_name,LocalDate fromDate,LocalDate toDate);

	@Query("select profile " + "from Profile profile " + "where ( ?1 =0L or profile.position.id=?1)"
			+ "and (?2 =0L or profile.department.id=?2)" + "and (?3 =0L or profile.status_profile.id=?3)"
			+ "and (?4='' or ?4 is null or profile.employee.full_name like %?4%)"
			+ "and profile.join_date between ?5 and ?6")
	List<Profile> findByMultiAttrWithRanger(Long positionId, Long departmentId, Long statusId,
			String full_name,LocalDate fromDate,LocalDate toDate);
	
	// @Query("select profile from Profile")
	// Page<Profile> findByAllE(Pageable pageable);
	// @Query("select profile from Profile profile where
	// profile.position_id.login = ?1")
	// Page<Profile> findByPosition(Pageable pageable,Long id);
}
