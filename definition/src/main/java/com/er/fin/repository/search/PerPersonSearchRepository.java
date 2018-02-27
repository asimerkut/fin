package com.er.fin.repository.search;

import com.er.fin.domain.PerPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PerPerson entity.
 */
public interface PerPersonSearchRepository extends ElasticsearchRepository<PerPerson, Long> {
}
