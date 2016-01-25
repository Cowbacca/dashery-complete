package uk.co.dashery.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class DasheryFrontEndApplication {

    @RequestMapping("/")
    String home() {
        return "index";
    }


    public static void main(String[] args) {
        SpringApplication.run(DasheryFrontEndApplication.class, args);
    }
}
