package com.er.fin.repository;

import com.er.fin.domain.HopFinansalHareketDetay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Spring Data JPA repository for the HopFinansalHareketDetay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopFinansalHareketDetayRepository extends JpaRepository<HopFinansalHareketDetay, Long> {

    @Modifying
    @Transactional
    @Query("delete from HopFinansalHareketDetay e where e.ilgi.id = :id")
    void deleteKarsiDetay(@Param("id")Long id);

    @Override
    @Query("select d from HopFinansalHareketDetay d where d.ilgi is null")
    Page<HopFinansalHareketDetay> findAll(Pageable var1);

    @Query("select d from HopFinansalHareketDetay d where d.finansalHareket.id = :fhId and d.ilgi is not null")
    List<HopFinansalHareketDetay> findKarsiFHD(@Param("fhId")Long fhId);

}
