package com.er.fin.repository.search;

import com.er.fin.domain.DosyaTipi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DosyaTipi entity.
 */
public interface DosyaTipiSearchRepository extends ElasticsearchRepository<DosyaTipi, Long> {
}
