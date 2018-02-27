package com.er.fin.repository;

import com.er.fin.domain.DefType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DefType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefTypeRepository extends JpaRepository<DefType, Long> {

}
