package com.er.fin.repository;

import com.er.fin.domain.HopBorc;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HopBorc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopBorcRepository extends JpaRepository<HopBorc, Long> {

}
