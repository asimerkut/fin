package com.er.fin.repository.search;

import com.er.fin.domain.IslemKodu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IslemKodu entity.
 */
public interface IslemKoduSearchRepository extends ElasticsearchRepository<IslemKodu, Long> {
}
