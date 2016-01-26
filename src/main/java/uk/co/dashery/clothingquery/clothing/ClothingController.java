package uk.co.dashery.clothingquery.clothing;

import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.dashery.ingestor.productfeed.ProductsCreatedEvent;

import javax.inject.Inject;
import java.util.List;

@RestController("/clothing")
public class ClothingController {

    @Inject
    private ClothingService clothingService;
    @Inject
    private ProductToClothingConverter productToClothingConverter;

    @CrossOrigin
    @RequestMapping
    public List<Clothing> clothing(@RequestParam String search) {
        return clothingService.search(search);
    }

    @EventListener
    public void handleProductsCreated(ProductsCreatedEvent productsCreatedEvent) {
        List<Clothing> clothingList = productToClothingConverter.convert(productsCreatedEvent.getProducts());
        clothingService.create(clothingList);
    }
}
