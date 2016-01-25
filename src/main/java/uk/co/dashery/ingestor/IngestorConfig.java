package uk.co.dashery.ingestor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import uk.co.dashery.ingestor.rabbitmq.RabbitMqConfig;

@SpringBootApplication
@Import(RabbitMqConfig.class)
public class IngestorConfig {

}
