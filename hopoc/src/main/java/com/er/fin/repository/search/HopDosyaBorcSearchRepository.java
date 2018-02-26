package com.er.fin.repository.search;

import com.er.fin.domain.HopDosyaBorc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HopDosyaBorc entity.
 */
public interface HopDosyaBorcSearchRepository extends ElasticsearchRepository<HopDosyaBorc, Long> {
}
