package com.er.fin.repository.search;

import com.er.fin.domain.BorcKalem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BorcKalem entity.
 */
public interface BorcKalemSearchRepository extends ElasticsearchRepository<BorcKalem, Long> {
}
