package uk.co.dashery.autocomplete.controller;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.dashery.autocomplete.TestAutocompleteConfig;
import uk.co.dashery.autocomplete.data.Token;
import uk.co.dashery.autocomplete.repository.TokenRepository;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static uk.co.dashery.autocomplete.TokenTestUtils.getTestJson;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAutocompleteConfig.class)
public class TokenControllerIT {

    @Inject
    private TokenRepository tokenRepository;

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private TokenController tokenController;

    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }

    @Test
    public void testDoesNotDuplicateExistingTokens() throws InterruptedException {
        tokenController.createTokensFromJson(getTestJson("Kylo", "Ren"));
        tokenController.createTokensFromJson(getTestJson("Kylo", "Ren"));

        waitForAsyncTasksToFinish();

        List<Token> tokens = tokenController.tokens();

        assertThat(tokens.size(), is(2));
    }

    private void waitForAsyncTasksToFinish() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    public void testCreatingTokensTakesUnder1Second() throws InterruptedException {
        String randomTags = IntStream.range(0, 5000)
                .boxed()
                .map(this::getRandomString)
                .collect(Collectors.joining(","));
        String json = reallyLongJson(randomTags);

        Instant start = Instant.now();
        tokenController.createTokensFromJson(json);
        Instant end = Instant.now();

        assertThat(Duration.between(start, end).getSeconds(), is(lessThan(1L)));
    }

    private String reallyLongJson(String randomTags) {
        return "[{tags: [" + randomTags + "]}]";
    }

    private String getRandomString(int i) {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
