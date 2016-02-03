package uk.co.dashery.autocomplete;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.autocomplete.TokenTestUtils.generateTokens;

public class TokenControllerTest {

    public static final String JSON = "test";

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private TokenService tokenService;
    @Mock
    private TokenRepository tokenRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testTokens() throws Exception {
        List<Token> allTokens = generateTokens();

        when(tokenService.findAll()).thenReturn(allTokens);

        assertThat(tokenController.tokens(), is(allTokens));
    }

    @Test
    public void testCreateTokensFromJson() throws Exception {
        tokenController.createTokensFromJson(JSON);

        verify(tokenService).createFromJson(JSON);
    }

    @Test
    public void testGetsTokensThatStartWithGivenValue() {
        withSomeTokensInTheRepository("sometoken", "othertoken");

        List<Token> tokensFromController = tokenController.getTokensBeginningWith("some");

        assertThat(tokensFromController, is(Lists.newArrayList(new Token("sometoken"))));
    }

    private void withSomeTokensInTheRepository(String... tokenStrings) {
        List<Token> tokens = getTokens(tokenStrings);
        when(tokenRepository.findAll()).thenReturn(tokens);
        tokenController.initAllTokens();
    }

    private List<Token> getTokens(String... tokenStrings) {
        return Arrays.stream(tokenStrings).map(Token::new).collect(Collectors.toList());
    }

    @Test
    public void testGetsOnlyTheFirstFiveTokensThatStartWithGivenValue() {
        withSomeTokensInTheRepository("sometoken1", "sometoken2", "sometoken3", "sometoken4", "sometoken5", "sometoken6");

        List<Token> tokensFromController = tokenController.getTokensBeginningWith("some");

        assertThat(tokensFromController.size(), is(5));
    }

    @Test
    public void testGetsTokensThatStartWithAGivenValueInOrderOfSizeThenAlphabetically() {
        withSomeTokensInTheRepository("aa", "ab", "aac", "a");

        List<Token> tokensFromController = tokenController.getTokensBeginningWith("a");

        assertThat(tokensFromController, is(getTokens("a", "aa", "ab", "aac")));

    }
}