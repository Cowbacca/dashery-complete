package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ClothingItemsPersistedEvent;

import javax.inject.Inject;

@Component
@Log
class IndexerController {
    private final ClothingIndexRepository clothingIndexRepository;

    @Inject
    IndexerController(ClothingIndexRepository clothingIndexRepository) {
        this.clothingIndexRepository = clothingIndexRepository;
    }

    @EventListener
    @Transactional
    void handleProductsCreatedEvent(ClothingItemsPersistedEvent clothingItemsPersistedEvent) throws AlgoliaException {
        String brand = clothingItemsPersistedEvent.getBrand();
        log.info("Deleting by brand: " + brand);
        clothingIndexRepository.deleteByBrand(brand);
        log.info("Saving clothing list.");
        clothingIndexRepository.save(clothingItemsPersistedEvent.getClothingItems());
    }
}
