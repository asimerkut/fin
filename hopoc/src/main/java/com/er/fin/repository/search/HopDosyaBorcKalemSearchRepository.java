package com.er.fin.repository.search;

import com.er.fin.domain.HopDosyaBorcKalem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HopDosyaBorcKalem entity.
 */
public interface HopDosyaBorcKalemSearchRepository extends ElasticsearchRepository<HopDosyaBorcKalem, Long> {
}
