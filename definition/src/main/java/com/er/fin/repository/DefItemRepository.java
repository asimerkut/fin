package com.er.fin.repository;

import com.er.fin.domain.DefItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the DefItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefItemRepository extends JpaRepository<DefItem, Long> {

    List<DefItem> findAllByTypeIdOrderByCode(Long typeId);

}
