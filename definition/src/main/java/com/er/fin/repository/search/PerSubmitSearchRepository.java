package com.er.fin.repository.search;

import com.er.fin.domain.PerSubmit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PerSubmit entity.
 */
public interface PerSubmitSearchRepository extends ElasticsearchRepository<PerSubmit, Long> {
}
