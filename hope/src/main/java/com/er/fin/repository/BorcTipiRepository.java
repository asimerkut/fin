package com.er.fin.repository;

import com.er.fin.domain.BorcTipi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BorcTipi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BorcTipiRepository extends JpaRepository<BorcTipi, Long> {

}
