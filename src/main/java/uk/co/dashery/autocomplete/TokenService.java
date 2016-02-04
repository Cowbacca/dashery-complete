package uk.co.dashery.autocomplete;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Component
public class TokenService {
    @Inject
    private TokenRepository tokenRepository;
    @Inject
    private TokenJsonParser tokenJsonParser;
    @Inject
    private ApplicationEventPublisher applicationEventPublisher;

    @Async
    public void createFromJson(String json) {
        List<Token> parsedTokens = tokenJsonParser.parse(json);
        tokenRepository.save(parsedTokens);
        applicationEventPublisher.publishEvent(new TokensCreatedEvent(parsedTokens, this));
    }

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }
}
