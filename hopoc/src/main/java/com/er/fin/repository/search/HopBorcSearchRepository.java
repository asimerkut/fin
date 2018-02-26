package com.er.fin.repository.search;

import com.er.fin.domain.HopBorc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HopBorc entity.
 */
public interface HopBorcSearchRepository extends ElasticsearchRepository<HopBorc, Long> {
}
