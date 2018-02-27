package com.er.fin.repository;

import com.er.fin.domain.PerPlan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PerPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerPlanRepository extends JpaRepository<PerPlan, Long> {

}
