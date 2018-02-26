package com.er.fin.repository.search;

import com.er.fin.domain.Masraf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Masraf entity.
 */
public interface MasrafSearchRepository extends ElasticsearchRepository<Masraf, Long> {
}
