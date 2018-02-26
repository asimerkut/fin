package com.er.fin.repository;

import com.er.fin.domain.Borc;
import com.er.fin.domain.DosyaBorc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Borc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BorcRepository extends JpaRepository<Borc, Long> {

    Page<Borc> findAllByDosyaId(Pageable pageable, Long dosyaId);

}
