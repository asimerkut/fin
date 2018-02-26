package com.er.fin.repository.search;

import com.er.fin.domain.FinansalHareket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FinansalHareket entity.
 */
public interface FinansalHareketSearchRepository extends ElasticsearchRepository<FinansalHareket, Long> {
}
