package com.er.fin.repository;

import com.er.fin.domain.PerSubmit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PerSubmit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerSubmitRepository extends JpaRepository<PerSubmit, Long> {

}
