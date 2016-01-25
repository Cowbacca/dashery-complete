package uk.co.dashery.clothingquerry.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.co.dashery.clothingquerry.clothing.Clothing;

import javax.inject.Inject;
import java.util.List;

@Service
public class TokenService {

    @Value("${dashery.autocomplete.url}")
    private String autocompleteURL;
    @Value("${dashery.autocomplete.creation.endpoint}")
    private String tokenCreationEndpoint;

    @Inject
    private RestTemplate restTemplate;

    public void createFromClothing(List<Clothing> clothing) {
        restTemplate.postForEntity(autocompleteURL + tokenCreationEndpoint, clothing, Clothing
                .class);
    }
}
