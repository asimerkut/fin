package com.er.fin.repository.search;

import com.er.fin.domain.DefRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DefRelation entity.
 */
public interface DefRelationSearchRepository extends ElasticsearchRepository<DefRelation, Long> {
}
