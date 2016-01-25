package uk.co.dashery.autocomplete;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import uk.co.dashery.TestMongoConfiguration;

@Configuration
@Import({TestMongoConfiguration.class})
public class TestAutocompleteConfig extends AutocompleteConfig {

}
