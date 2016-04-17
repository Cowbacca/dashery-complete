package uk.co.dashery.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResultsController {

    @RequestMapping("/")
    String home() {
        return "index";
    }

    @RequestMapping("/results")
    String results() {
        return "results";
    }
}
