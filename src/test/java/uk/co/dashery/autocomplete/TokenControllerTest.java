package uk.co.dashery.autocomplete;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.autocomplete.TokenTestUtils.generateTokens;

public class TokenControllerTest {

    public static final String JSON = "test";
    public static final String BEGINNING_OF_TAG = "some";
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
        HashSet<Token> tokens = Sets.newHashSet(new Token("sometoken"), new Token("someother"));
        when(tokenRepository.findByValueStartsWith(BEGINNING_OF_TAG)).thenReturn(tokens);

        Set<Token> tokensFromController = tokenController.getTokensBeginningWith(BEGINNING_OF_TAG);

        assertThat(tokensFromController, is(tokens));
    }
}