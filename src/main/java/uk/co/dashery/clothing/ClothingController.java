package uk.co.dashery.clothing;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.common.ProductsCreatedEvent;

import javax.inject.Inject;
import java.util.List;

@Controller
class ClothingController {
    private final ProductToClothingConverter productToClothingConverter;
    private final ClothingRepository clothingRepository;

    @Inject
    ClothingController(ProductToClothingConverter productToClothingConverter, ClothingRepository clothingRepository) {
        this.productToClothingConverter = productToClothingConverter;
        this.clothingRepository = clothingRepository;
    }


    @Async
    @EventListener
    @Transactional
    void handleProductsCreated(ProductsCreatedEvent productsCreatedEvent) {
        List<Clothing> clothingList = productToClothingConverter.convert(productsCreatedEvent.getProducts());

        if (!clothingList.isEmpty()) {
            deleteExistingAndSaveNew(productsCreatedEvent.getBrand(), clothingList);
        }
    }

    private void deleteExistingAndSaveNew(String brand, List<Clothing> clothingList) {
        clothingRepository.deleteByBrand(brand);
        clothingRepository.save(clothingList);
    }
}
