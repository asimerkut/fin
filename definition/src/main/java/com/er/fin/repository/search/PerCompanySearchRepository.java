package com.er.fin.repository.search;

import com.er.fin.domain.PerCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PerCompany entity.
 */
public interface PerCompanySearchRepository extends ElasticsearchRepository<PerCompany, Long> {
}
