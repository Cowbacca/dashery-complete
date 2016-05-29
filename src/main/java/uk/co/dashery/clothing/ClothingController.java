package uk.co.dashery.clothing;

import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.common.ClothingItemsPersistedEvent;
import uk.co.dashery.common.ProductFeedIngestedEvent;

import javax.inject.Inject;
import java.util.List;

@Controller
@Log
class ClothingController {
    private final ClothingRepository clothingRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Inject
    ClothingController(ClothingRepository clothingRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.clothingRepository = clothingRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Async
    @EventListener
    @Transactional
    void handleProductFeedIngested(ProductFeedIngestedEvent productFeedIngestedEvent) {
        List<ClothingItem> clothingItems = productFeedIngestedEvent.getClothingItems();

        if (!clothingItems.isEmpty()) {
            String brand = productFeedIngestedEvent.getBrand();
            deleteExistingAndSaveNew(brand, clothingItems);
            applicationEventPublisher.publishEvent(new ClothingItemsPersistedEvent(brand, clothingItems));
        }
    }

    private void deleteExistingAndSaveNew(String brand, List<ClothingItem> clothingList) {
        log.info("Deleting by brand: " + brand);
        clothingRepository.deleteByBrand(brand);
        log.info("Saving clothing list.");
        clothingRepository.save(clothingList);
    }
}
