package com.er.fin.repository.search;

import com.er.fin.domain.DosyaBorcKalem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DosyaBorcKalem entity.
 */
public interface DosyaBorcKalemSearchRepository extends ElasticsearchRepository<DosyaBorcKalem, Long> {
}
