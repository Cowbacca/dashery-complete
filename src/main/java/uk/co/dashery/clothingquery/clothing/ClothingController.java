package uk.co.dashery.clothingquery.clothing;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController("/clothing")
public class ClothingController {

    @Inject
    private ClothingService clothingService;

    @CrossOrigin
    @RequestMapping
    public List<Clothing> clothing(@RequestParam String search) {
        return clothingService.search(search);
    }

    @RabbitListener(queues = "products")
    public void processNewClothing(List<Clothing> clothing) {
        clothingService.create(clothing);
    }
}
