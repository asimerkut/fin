package com.er.fin.repository.search;

import com.er.fin.domain.Dosya;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Dosya entity.
 */
public interface DosyaSearchRepository extends ElasticsearchRepository<Dosya, Long> {
}
