package uk.co.dashery.indexer;

import com.algolia.search.saas.APIClient;
import com.algolia.search.saas.Index;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexerConfig {

    @Value("${algoliasearch.application.id}")
    private String applicationId;

    @Value("${algoliasearch.api.key}")
    private String key;

    @Value("${indexer.index.name}")
    private String clothingIndexName;

    @Bean
    public APIClient apiClient() {
        return new APIClient(applicationId, key);
    }

    @Bean
    public Index clothingIndex() {
        return apiClient().initIndex(clothingIndexName);
    }
}
