package uk.co.dashery;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableAsync
public class DasheryCompleteApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(DasheryCompleteApplication.class, args);

        FileUtils.touch(new File("/tmp/app-initialized"));
    }
}
