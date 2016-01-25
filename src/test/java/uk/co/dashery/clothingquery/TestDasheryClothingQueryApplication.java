package uk.co.dashery.clothingquery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@Configuration
@Import({TestMongoConfiguration.class})
public class TestDasheryClothingQueryApplication extends DasheryClothingQueryApplication {

    @Bean
    public RestTemplate restTemplate() {
        return mock(RestTemplate.class);
    }
}
