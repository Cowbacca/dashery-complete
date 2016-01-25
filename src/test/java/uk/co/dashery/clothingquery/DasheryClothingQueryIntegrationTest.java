package uk.co.dashery.clothingquery;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ContextConfiguration(classes = TestDasheryClothingQueryApplication.class)
@TestPropertySource("classpath:service-test.properties")
public @interface DasheryClothingQueryIntegrationTest {
}
