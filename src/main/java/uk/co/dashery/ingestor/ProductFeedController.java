package uk.co.dashery.ingestor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.common.ProductFeedIngestedEvent;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller
class ProductFeedController {

    @Inject
    private ProductFeedFactory productFeedFactory;
    @Inject
    private ApplicationEventPublisher applicationEventPublisher;

    @RequestMapping(value = "/productFeed", method = RequestMethod.GET)
    String productsForm(Model model) {
        model.addAttribute("productFeed", new ProductFeedForm());
        return "productFeed";
    }

    @RequestMapping(value = "/productFeed", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    void ingestProducts(@ModelAttribute ProductFeedForm productFeedForm) throws IOException {
        ProductFeed productFeed = productFeedFactory.create(productFeedForm);
        List<ClothingItem> clothingItems = productFeed.getClothingItems();
        applicationEventPublisher.publishEvent(new ProductFeedIngestedEvent(merchant(clothingItems), clothingItems));
    }

    private String merchant(List<ClothingItem> clothingItems) {
        return clothingItems.get(0).getBrand();
    }
}
