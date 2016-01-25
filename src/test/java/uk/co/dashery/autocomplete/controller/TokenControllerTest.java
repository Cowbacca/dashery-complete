package uk.co.dashery.autocomplete.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uk.co.dashery.autocomplete.data.Token;
import uk.co.dashery.autocomplete.service.TokenService;

import java.util.List;

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
}