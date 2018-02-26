package com.er.fin.repository.search;

import com.er.fin.domain.Borc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Borc entity.
 */
public interface BorcSearchRepository extends ElasticsearchRepository<Borc, Long> {
}
