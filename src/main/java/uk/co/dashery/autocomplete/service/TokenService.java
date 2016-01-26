package uk.co.dashery.autocomplete.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.co.dashery.autocomplete.data.Token;
import uk.co.dashery.autocomplete.repository.TokenRepository;

import javax.inject.Inject;
import java.util.List;

@Service
@Component
public class TokenService {
    @Inject
    private TokenRepository tokenRepository;
    @Inject
    private TokenJsonParser tokenJsonParser;

    @Async
    public void createFromJson(String json) {
        List<Token> parsedTokens = tokenJsonParser.parse(json);
        tokenRepository.save(parsedTokens);
    }

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }
}
