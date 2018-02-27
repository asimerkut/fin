package com.er.fin.repository.search;

import com.er.fin.domain.DefItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DefItem entity.
 */
public interface DefItemSearchRepository extends ElasticsearchRepository<DefItem, Long> {
}
