package com.er.fin.repository.search;

import com.er.fin.domain.BorcTipi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BorcTipi entity.
 */
public interface BorcTipiSearchRepository extends ElasticsearchRepository<BorcTipi, Long> {
}
