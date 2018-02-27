package com.er.fin.repository;

import com.er.fin.domain.DefAnswer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DefAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefAnswerRepository extends JpaRepository<DefAnswer, Long> {

}
