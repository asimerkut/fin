package com.er.fin.repository;

import com.er.fin.domain.PerPerson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PerPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerPersonRepository extends JpaRepository<PerPerson, Long> {

    @Query("select per_person from PerPerson per_person where per_person.user.login = ?#{principal.username}")
    List<PerPerson> findByUserIsCurrentUser();

}
