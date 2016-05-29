package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ProductsCreatedEvent;

import javax.inject.Inject;

@Component
public class IndexerController {
    private final ClothingIndexRepository clothingIndexRepository;

    @Inject
    public IndexerController(ClothingIndexRepository clothingIndexRepository) {
        this.clothingIndexRepository = clothingIndexRepository;
    }

    @EventListener
    @Transactional
    public void handleProductsCreatedEvent(ProductsCreatedEvent productsCreatedEvent) throws AlgoliaException {
        clothingIndexRepository.deleteByBrand(productsCreatedEvent.getBrand());
        clothingIndexRepository.save(productsCreatedEvent.getProducts());
    }
}
