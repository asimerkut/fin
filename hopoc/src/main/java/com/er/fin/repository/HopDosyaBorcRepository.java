package com.er.fin.repository;

import com.er.fin.domain.HopDosyaBorc;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HopDosyaBorc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopDosyaBorcRepository extends JpaRepository<HopDosyaBorc, Long> {

}
