package uk.co.dashery.autocomplete;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@EqualsAndHashCode
public class TokensCreatedEvent extends ApplicationEvent {
    @Getter
    private final List<Token> parsedTokens;

    public TokensCreatedEvent(List<Token> parsedTokens, Object source) {
        super(source);
        this.parsedTokens = parsedTokens;
    }
}
