package uk.co.dashery.clothingquery;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class TestMongoConfiguration extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "demo-test";
    }

    @Override
    public Mongo mongo() {
        return new Fongo("mongo-test").getMongo();
    }
}
