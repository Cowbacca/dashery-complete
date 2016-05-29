package uk.co.dashery.clothing;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.common.ProductFeedIngestedEvent;

import javax.inject.Inject;
import java.util.List;

@Controller
class ClothingController {
    private final ClothingRepository clothingRepository;

    @Inject
    ClothingController(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }


    @Async
    @EventListener
    @Transactional
    void handleProductFeedIngested(ProductFeedIngestedEvent productFeedIngestedEvent) {
        List<ClothingItem> clothingItems = productFeedIngestedEvent.getClothingItems();

        if (!clothingItems.isEmpty()) {
            deleteExistingAndSaveNew(productFeedIngestedEvent.getBrand(), clothingItems);
        }
    }

    private void deleteExistingAndSaveNew(String brand, List<ClothingItem> clothingList) {
        clothingRepository.deleteByBrand(brand);
        clothingRepository.save(clothingList);
    }
}
