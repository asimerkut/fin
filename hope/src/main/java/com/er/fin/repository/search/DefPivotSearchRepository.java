package com.er.fin.repository.search;

import com.er.fin.domain.DefPivot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DefPivot entity.
 */
public interface DefPivotSearchRepository extends ElasticsearchRepository<DefPivot, Long> {
}
