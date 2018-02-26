package com.er.fin.repository.search;

import com.er.fin.domain.HopFinansalHareket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HopFinansalHareket entity.
 */
public interface HopFinansalHareketSearchRepository extends ElasticsearchRepository<HopFinansalHareket, Long> {
}
