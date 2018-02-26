package com.er.fin.repository;

import com.er.fin.domain.Dosya;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dosya entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DosyaRepository extends JpaRepository<Dosya, Long> {

}
