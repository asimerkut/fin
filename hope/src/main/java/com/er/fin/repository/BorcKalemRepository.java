package com.er.fin.repository;

import com.er.fin.domain.BorcKalem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BorcKalem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BorcKalemRepository extends JpaRepository<BorcKalem, Long> {

}
