package com.er.fin.repository.search;

import com.er.fin.domain.HopMasraf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HopMasraf entity.
 */
public interface HopMasrafSearchRepository extends ElasticsearchRepository<HopMasraf, Long> {
}
