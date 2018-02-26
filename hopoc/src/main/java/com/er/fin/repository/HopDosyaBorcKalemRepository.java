package com.er.fin.repository;

import com.er.fin.domain.HopDosyaBorcKalem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HopDosyaBorcKalem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopDosyaBorcKalemRepository extends JpaRepository<HopDosyaBorcKalem, Long> {

}
