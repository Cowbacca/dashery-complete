package uk.co.dashery.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class ResultsController {

    @Value("${dashery.clothing-query.url}")
    private String clothingQueryURL;
    @Value("${dashery.clothing-query.search.endpoint}")
    private String clothingSearchEndpoint;

    @RequestMapping("/results")
    String results(@RequestParam(defaultValue = "") String search, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Clothing[] clothing = restTemplate.getForObject(clothingQueryURL + clothingSearchEndpoint + search, Clothing[].class);
        model.addAttribute("clothing", clothing);
        return "results";
    }
}
