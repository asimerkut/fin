package com.er.fin.repository;

import com.er.fin.domain.DosyaBorc;
import com.er.fin.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the DosyaBorc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DosyaBorcRepository extends JpaRepository<DosyaBorc, Long> {

    Page<DosyaBorc> findAllByDosyaId(Pageable pageable, Long dosyaId);

}
