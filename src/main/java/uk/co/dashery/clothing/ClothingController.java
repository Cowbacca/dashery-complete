package uk.co.dashery.clothing;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import uk.co.dashery.ingestor.productfeed.ProductsCreatedEvent;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ClothingController {
    private final ProductToClothingConverter productToClothingConverter;
    private final ClothingRepository clothingRepository;

    @Inject
    public ClothingController(ProductToClothingConverter productToClothingConverter, ClothingRepository clothingRepository) {
        this.productToClothingConverter = productToClothingConverter;
        this.clothingRepository = clothingRepository;
    }


    @Async
    @EventListener
    @Transactional
    public void handleProductsCreated(ProductsCreatedEvent productsCreatedEvent) {
        List<Clothing> clothingList = productToClothingConverter.convert(productsCreatedEvent.getProducts());

        if (!clothingList.isEmpty()) {
            deleteExistingAndSaveNew(clothingList);
        }
    }

    private void deleteExistingAndSaveNew(List<Clothing> clothingList) {
        String brand = clothingList.get(0).getBrand();
        clothingRepository.deleteByBrand(brand);
        clothingRepository.save(clothingList);
    }
}
