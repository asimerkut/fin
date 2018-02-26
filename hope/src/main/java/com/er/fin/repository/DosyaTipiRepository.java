package com.er.fin.repository;

import com.er.fin.domain.DosyaTipi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DosyaTipi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DosyaTipiRepository extends JpaRepository<DosyaTipi, Long> {

}
