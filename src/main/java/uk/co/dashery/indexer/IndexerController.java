package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ClothingItemsPersistedEvent;

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
    void handleProductsCreatedEvent(ClothingItemsPersistedEvent clothingItemsPersistedEvent) throws AlgoliaException {
        clothingIndexRepository.deleteByBrand(clothingItemsPersistedEvent.getBrand());
        clothingIndexRepository.save(clothingItemsPersistedEvent.getClothingItems());
    }
}
