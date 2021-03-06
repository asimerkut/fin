package com.er.fin.repository;

import com.er.fin.domain.BorcGrubu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BorcGrubu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BorcGrubuRepository extends JpaRepository<BorcGrubu, Long> {

}
