package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import org.json.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uk.co.dashery.ingestor.productfeed.ProductsCreatedEvent;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndexerController {
    @Inject
    private Index clothingIndex;

    @EventListener
    public void handleProductsCreatedEvent(ProductsCreatedEvent productsCreatedEvent) throws AlgoliaException {
        List<JSONObject> clothingItems = productsCreatedEvent.getProducts().stream()
                .map(product -> new JSONObject()
                        .put("objectID", product.getId() + "-" + product.getMerchant())
                        .put("brand", product.getMerchant())
                        .put("name", product.getName())
                        .put("price", product.getPrice())
                        .put("link", product.getLink())
                        .put("imageLink", product.getImageLink())
                        .put("description", product.getDescription()))
                .collect(Collectors.toList());
        clothingIndex.saveObjects(clothingItems);
    }
}
