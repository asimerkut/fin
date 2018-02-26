package com.er.fin.repository;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.domain.DosyaBorcKalem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DosyaBorcKalem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DosyaBorcKalemRepository extends JpaRepository<DosyaBorcKalem, Long> {

    Page<DosyaBorcKalem> findAllByDosyaBorcDosyaId(Pageable pageable, Long dosyaId);

}
