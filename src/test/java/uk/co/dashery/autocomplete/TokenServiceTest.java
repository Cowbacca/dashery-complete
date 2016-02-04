package uk.co.dashery.autocomplete;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.autocomplete.TokenTestUtils.generateTokens;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private TokenJsonParser tokenJsonParser;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    ;

    @Test
    public void testCreateFromJson() throws Exception {
        List<Token> parsedTokens = generateTokens();
        when(tokenJsonParser.parse("test")).thenReturn(parsedTokens);

        tokenService.createFromJson("test");

        verify(tokenRepository).save(parsedTokens);
        verify(applicationEventPublisher).publishEvent(new TokensCreatedEvent(parsedTokens,
                tokenService));

    }

    @Test
    public void testFindAll() throws Exception {
        List<Token> allTokens = generateTokens();
        when(tokenRepository.findAll()).thenReturn(allTokens);

        assertThat(tokenService.findAll(), is(allTokens));
    }
}