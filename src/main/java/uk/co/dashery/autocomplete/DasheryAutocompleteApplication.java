package uk.co.dashery.autocomplete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DasheryAutocompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasheryAutocompleteApplication.class, args);
    }
}
