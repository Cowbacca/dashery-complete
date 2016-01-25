package uk.co.dashery.frontend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class FrontEndConfig {

    @RequestMapping("/")
    String home() {
        return "index";
    }
}
