package com.er.fin.repository.search;

import com.er.fin.domain.PerExcuse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PerExcuse entity.
 */
public interface PerExcuseSearchRepository extends ElasticsearchRepository<PerExcuse, Long> {
}
