package com.er.fin.repository;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.domain.FinansalHareket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FinansalHareket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinansalHareketRepository extends JpaRepository<FinansalHareket, Long> {

    Page<FinansalHareket> findAllByDosyaId(Pageable pageable, Long dosyaId);

}
