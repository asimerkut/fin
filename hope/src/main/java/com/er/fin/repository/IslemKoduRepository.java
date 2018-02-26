package com.er.fin.repository;

import com.er.fin.domain.IslemKodu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IslemKodu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IslemKoduRepository extends JpaRepository<IslemKodu, Long> {

}
