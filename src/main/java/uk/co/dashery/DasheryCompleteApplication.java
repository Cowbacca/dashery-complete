package uk.co.dashery;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import uk.co.dashery.clothing.ClothingConfig;
import uk.co.dashery.frontend.FrontEndConfig;
import uk.co.dashery.ingestor.IngestorConfig;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@Import({ClothingConfig.class, FrontEndConfig.class, IngestorConfig.class})
public class DasheryCompleteApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(DasheryCompleteApplication.class, args);

        FileUtils.touch(new File("/tmp/app-initialized"));
    }
}
