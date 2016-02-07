package uk.co.dashery.clothingquery.clothing;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.dashery.clothingquery.ClothingAddedEvent;
import uk.co.dashery.ingestor.productfeed.ProductsCreatedEvent;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ClothingController {

    @Inject
    private ClothingService clothingService;
    @Inject
    private ProductToClothingConverter productToClothingConverter;
    @Inject
    private ClothingRepository clothingRepository;
    @Inject
    private ApplicationEventPublisher applicationEventPublisher;

    public List<Clothing> clothing(@RequestParam String search) {
        return clothingService.search(search);
    }

    @Async
    @EventListener
    @Transactional
    public void handleProductsCreated(ProductsCreatedEvent productsCreatedEvent) {
        List<Clothing> clothingList = productToClothingConverter.convert(productsCreatedEvent.getProducts());

        if (!clothingList.isEmpty()) {
            deleteExistingAndSaveNew(clothingList);
            applicationEventPublisher.publishEvent(new ClothingAddedEvent(clothingList));
        }
    }

    private void deleteExistingAndSaveNew(List<Clothing> clothingList) {
        String brand = clothingList.get(0).getBrand();
        clothingRepository.deleteByBrand(brand);
        clothingRepository.save(clothingList);
    }
}
