package com.er.fin.repository;

import com.er.fin.domain.HopDosya;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HopDosya entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopDosyaRepository extends JpaRepository<HopDosya, Long> {

}
