package com.er.fin.repository;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.domain.FinansalHareketDetay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FinansalHareketDetay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinansalHareketDetayRepository extends JpaRepository<FinansalHareketDetay, Long> {

    Page<FinansalHareketDetay> findAllByFinansalHareketDosyaId(Pageable pageable, Long dosyaId);

}
