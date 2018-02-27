package com.er.fin.repository;

import com.er.fin.domain.DefPivot;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DefPivot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefPivotRepository extends JpaRepository<DefPivot, Long> {

}
