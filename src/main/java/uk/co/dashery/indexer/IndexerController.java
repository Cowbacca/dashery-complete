package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ProductsCreatedEvent;

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
    void handleProductsCreatedEvent(ProductsCreatedEvent productsCreatedEvent) throws AlgoliaException {
        clothingIndexRepository.deleteByBrand(productsCreatedEvent.getBrand());
        clothingIndexRepository.save(productsCreatedEvent.getProducts());
    }
}
