package uk.co.dashery.clothingquery;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import uk.co.dashery.TestMongoConfiguration;

@Configuration
@Import({TestMongoConfiguration.class})
@EnableMBeanExport(defaultDomain = "clothingtest")
public class TestDasheryClothingQueryApplication extends ClothingQueryConfig {

}
