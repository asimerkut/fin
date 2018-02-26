package com.er.fin.repository.search;

import com.er.fin.domain.FinansalHareketDetay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FinansalHareketDetay entity.
 */
public interface FinansalHareketDetaySearchRepository extends ElasticsearchRepository<FinansalHareketDetay, Long> {
}
