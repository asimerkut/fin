package com.er.fin.repository.search;

import com.er.fin.domain.DefType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DefType entity.
 */
public interface DefTypeSearchRepository extends ElasticsearchRepository<DefType, Long> {
}
