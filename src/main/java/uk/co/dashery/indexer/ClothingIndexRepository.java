package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import uk.co.dashery.ingestor.productfeed.Product;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClothingIndexRepository {
    private final Index clothingIndex;

    @Inject
    public ClothingIndexRepository(Index clothingIndex) {
        this.clothingIndex = clothingIndex;
    }

    public void save(Collection<Product> products) {
        List<JSONObject> clothingItems = products.stream()
                .map(Product::toJsonObject)
                .collect(Collectors.toList());

        saveObjects(clothingItems);
    }

    private void saveObjects(List<JSONObject> clothingItems) {
        try {
            clothingIndex.saveObjects(clothingItems);
        } catch (AlgoliaException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByBrand(String brand) {
        Query findByBrand = new Query("").setFacetFilters("brand:" + brand);
        deleteByQuery(findByBrand);
    }

    private void deleteByQuery(Query findByBrand) {
        try {
            clothingIndex.deleteByQuery(findByBrand);
        } catch (AlgoliaException e) {
            throw new RuntimeException(e);
        }
    }
}
