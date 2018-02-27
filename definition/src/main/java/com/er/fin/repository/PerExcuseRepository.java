package com.er.fin.repository;

import com.er.fin.domain.PerExcuse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PerExcuse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerExcuseRepository extends JpaRepository<PerExcuse, Long> {

}
