package uk.co.dashery.ingestor.productfeed;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductFeedController {

    @Inject
    private ProductFeedFactory productFeedFactory;
    @Inject
    private ApplicationEventPublisher applicationEventPublisher;

    @RequestMapping(value = "/productFeed", method = RequestMethod.GET)
    public String productsForm(Model model) {
        model.addAttribute("productFeed", new ProductFeedForm());
        return "productFeed";
    }

    @RequestMapping(value = "/productFeed", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void ingestProducts(@ModelAttribute ProductFeedForm productFeedForm) throws IOException {
        ProductFeed productFeed = productFeedFactory.create(productFeedForm);
        List<Product> products = productFeed.getProducts();
        applicationEventPublisher.publishEvent(new ProductsCreatedEvent(products));
    }
}
