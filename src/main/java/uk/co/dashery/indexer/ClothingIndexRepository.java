package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import uk.co.dashery.common.ClothingItem;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
class ClothingIndexRepository {
    private final Index clothingIndex;

    @Inject
    ClothingIndexRepository(Index clothingIndex) {
        this.clothingIndex = clothingIndex;
    }

    void save(Collection<ClothingItem> products) {
        List<JSONObject> clothingItems = products.stream()
                .map(ClothingItem::toJsonObject)
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

    void deleteByBrand(String brand) {
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
