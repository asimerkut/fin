package com.er.fin.repository;

import com.er.fin.domain.DefRelation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DefRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefRelationRepository extends JpaRepository<DefRelation, Long> {

}
