package com.er.fin.repository.search;

import com.er.fin.domain.PerPlan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PerPlan entity.
 */
public interface PerPlanSearchRepository extends ElasticsearchRepository<PerPlan, Long> {
}
