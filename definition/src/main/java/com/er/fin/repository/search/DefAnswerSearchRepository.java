package com.er.fin.repository.search;

import com.er.fin.domain.DefAnswer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DefAnswer entity.
 */
public interface DefAnswerSearchRepository extends ElasticsearchRepository<DefAnswer, Long> {
}
