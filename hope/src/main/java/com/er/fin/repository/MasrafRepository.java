package com.er.fin.repository;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.domain.Masraf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Masraf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MasrafRepository extends JpaRepository<Masraf, Long> {

    Page<Masraf> findAllByDosyaId(Pageable pageable, Long dosyaId);

}
