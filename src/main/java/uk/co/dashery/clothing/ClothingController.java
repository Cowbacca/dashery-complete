package uk.co.dashery.clothing;

import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.clothing.image.ImageTransformer;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.common.ClothingItemsPersistedEvent;
import uk.co.dashery.common.ProductFeedIngestedEvent;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Controller
@Log
class ClothingController {
    private final ClothingRepository clothingRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ImageTransformer imageTransformer;

    @Inject
    ClothingController(ClothingRepository clothingRepository, ApplicationEventPublisher applicationEventPublisher, ImageTransformer imageTransformer) {
        this.clothingRepository = clothingRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.imageTransformer = imageTransformer;
    }


    @Async
    @EventListener
    @Transactional
    void handleProductFeedIngested(ProductFeedIngestedEvent productFeedIngestedEvent) {
        String brand = productFeedIngestedEvent.getBrand();
        List<ClothingItem> clothingItems = productFeedIngestedEvent.getClothingItems();

        if (!clothingItems.isEmpty()) {
            Set<ClothingItem> transformedClothingItems = transformClothingItemsImages(clothingItems);
            deleteExistingAndSaveNew(brand, transformedClothingItems);
            applicationEventPublisher.publishEvent(new ClothingItemsPersistedEvent(brand, transformedClothingItems));
        }
    }

    private Set<ClothingItem> transformClothingItemsImages(List<ClothingItem> clothingItems) {
        return clothingItems.stream()
                .map(clothingItem -> clothingItem.transformImage(imageTransformer))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSet());
    }

    private void deleteExistingAndSaveNew(String brand, Set<ClothingItem> clothingList) {
        log.info("Deleting by brand: " + brand);
        clothingRepository.deleteByBrand(brand);
        log.info("Saving clothing list.");
        clothingRepository.save(clothingList);
    }
}
