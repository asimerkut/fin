package com.er.fin.repository;

import com.er.fin.domain.PerCompany;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PerCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerCompanyRepository extends JpaRepository<PerCompany, Long> {

}
