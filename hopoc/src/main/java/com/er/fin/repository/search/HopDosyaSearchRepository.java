package com.er.fin.repository.search;

import com.er.fin.domain.HopDosya;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HopDosya entity.
 */
public interface HopDosyaSearchRepository extends ElasticsearchRepository<HopDosya, Long> {
}
