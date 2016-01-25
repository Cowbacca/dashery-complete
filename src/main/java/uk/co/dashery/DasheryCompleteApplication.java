package uk.co.dashery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import uk.co.dashery.autocomplete.AutocompleteConfig;
import uk.co.dashery.clothingquery.ClothingQueryConfig;
import uk.co.dashery.frontend.FrontEndConfig;
import uk.co.dashery.ingestor.IngestorConfig;

@SpringBootApplication
@Import({AutocompleteConfig.class, ClothingQueryConfig.class, FrontEndConfig.class, IngestorConfig.class})
public class DasheryCompleteApplication {
    public static void main(String[] args) {
        SpringApplication.run(DasheryCompleteApplication.class, args);
    }
}
