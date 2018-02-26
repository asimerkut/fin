package com.er.fin.repository.search;

import com.er.fin.domain.MasrafTipi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MasrafTipi entity.
 */
public interface MasrafTipiSearchRepository extends ElasticsearchRepository<MasrafTipi, Long> {
}
