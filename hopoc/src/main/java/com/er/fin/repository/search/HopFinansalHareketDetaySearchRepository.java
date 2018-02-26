package com.er.fin.repository.search;

import com.er.fin.domain.HopFinansalHareketDetay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HopFinansalHareketDetay entity.
 */
public interface HopFinansalHareketDetaySearchRepository extends ElasticsearchRepository<HopFinansalHareketDetay, Long> {
}
