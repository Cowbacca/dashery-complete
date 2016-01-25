package uk.co.dashery.clothingquery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import uk.co.dashery.clothingquery.rabbitmq.RabbitMqConfig;

@SpringBootApplication
@Import(RabbitMqConfig.class)
public class ClothingQueryConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
