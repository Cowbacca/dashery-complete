package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ProductFeedIngestedEvent;

import javax.inject.Inject;

@Component
class IndexerController {
    private final ClothingIndexRepository clothingIndexRepository;

    @Inject
    IndexerController(ClothingIndexRepository clothingIndexRepository) {
        this.clothingIndexRepository = clothingIndexRepository;
    }

    @EventListener
    @Transactional
    void handleProductsCreatedEvent(ProductFeedIngestedEvent productFeedIngestedEvent) throws AlgoliaException {
        clothingIndexRepository.deleteByBrand(productFeedIngestedEvent.getBrand());
        clothingIndexRepository.save(productFeedIngestedEvent.getClothingItems());
    }
}
