package com.er.fin.repository;

import com.er.fin.domain.MasrafTipi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MasrafTipi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MasrafTipiRepository extends JpaRepository<MasrafTipi, Long> {

}
