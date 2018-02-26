package com.er.fin.repository.search;

import com.er.fin.domain.BorcGrubu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BorcGrubu entity.
 */
public interface BorcGrubuSearchRepository extends ElasticsearchRepository<BorcGrubu, Long> {
}
