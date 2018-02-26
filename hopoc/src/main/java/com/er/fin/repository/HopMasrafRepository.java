package com.er.fin.repository;

import com.er.fin.domain.HopMasraf;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HopMasraf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopMasrafRepository extends JpaRepository<HopMasraf, Long> {

}
