package uk.co.dashery.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.dashery.clothingquery.clothing.Clothing;
import uk.co.dashery.clothingquery.clothing.ClothingController;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ResultsController {

    @Inject
    private ClothingController clothingController;
    @Inject
    private FrontEndClothingConverter frontEndClothingConverter;

    @RequestMapping("/")
    String home() {
        return "index";
    }

    @RequestMapping("/results")
    String results(@RequestParam(defaultValue = "") String search, Model model) {
        List<Clothing> clothing = clothingController.clothing(search.toLowerCase());
        model.addAttribute("clothing", frontEndClothingConverter.convert(clothing));
        return "results";
    }
}
