package uk.co.dashery.autocomplete;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.autocomplete.TokenTestUtils.getTestJson;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAutocompleteConfig.class)
public class TokenJsonParserTest {

    public static final String VALUE = "test value";
    public static final String ANOTHER_VALUE = "different value";
    @Inject
    private TokenJsonParser tokenJsonParser;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testParse() throws Exception {
        List<Token> expectedTokens = generateExpectedTokens();

        String json = getTestJson(VALUE, ANOTHER_VALUE);

        List<Token> parsedTokens = tokenJsonParser.parse(json);

        assertThat(parsedTokens, is(expectedTokens));
    }

    private List<Token> generateExpectedTokens() {
        List<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token(VALUE));
        expectedTokens.add(new Token(ANOTHER_VALUE));
        return expectedTokens;
    }
}