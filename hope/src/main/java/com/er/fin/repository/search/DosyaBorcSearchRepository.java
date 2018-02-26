package com.er.fin.repository.search;

import com.er.fin.domain.DosyaBorc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DosyaBorc entity.
 */
public interface DosyaBorcSearchRepository extends ElasticsearchRepository<DosyaBorc, Long> {
}
