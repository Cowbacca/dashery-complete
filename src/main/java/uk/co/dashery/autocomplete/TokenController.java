package uk.co.dashery.autocomplete;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import uk.co.dashery.clothingquery.ClothingAddedEvent;
import uk.co.dashery.clothingquery.clothing.Clothing;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    @Inject
    private TokenService tokenService;
    @Inject
    private TokenRepository tokenRepository;

    @CrossOrigin
    @RequestMapping(value = "/tokens/autocomplete", method = RequestMethod.GET)
    public List<Token> tokens() {
        return tokenService.findAll();
    }

    @RequestMapping(value = "/tokens/{beginning}", method = RequestMethod.GET)
    public Set<Token> getTokensBeginningWith(@PathVariable String beginning) {
        return tokenRepository.findByValueStartsWith(beginning);
    }

    @RequestMapping(value = "/tokens/json", method = RequestMethod.POST)
    public void createTokensFromJson(@RequestBody String json) {
        tokenService.createFromJson(json);
    }

    @Async
    @EventListener
    public void handleClothingAdded(ClothingAddedEvent clothingAddedEvent) {
        List<Clothing> clothingList = clothingAddedEvent.getClothingList();
        Set<Token> tokens = extractTokens(clothingList);

        tokenRepository.save(tokens);
    }

    private Set<Token> extractTokens(List<Clothing> clothingList) {
        return clothingList.stream()
                .flatMap(clothing -> clothing.getTags().stream())
                .map(tag -> new Token(tag.getValue()))
                .collect(Collectors.toSet());
    }
}
