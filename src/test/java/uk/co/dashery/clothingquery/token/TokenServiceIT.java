package uk.co.dashery.clothingquery.token;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import uk.co.dashery.DasheryClothingQueryIntegrationTest;
import uk.co.dashery.clothing.Clothing;

import javax.inject.Inject;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static uk.co.dashery.ClothingTestUtils.createClothing;

@RunWith(SpringJUnit4ClassRunner.class)
@DasheryClothingQueryIntegrationTest
public class TokenServiceIT {

    @Inject
    private TokenService tokenService;

    @Inject
    private RestTemplate restTemplate;
    @Value("${dashery.autocomplete.url}")
    private String autocompleteURL;
    @Value("${dashery.autocomplete.creation.endpoint}")
    private String tokenCreationEndpoint;

    @Before
    public void setUp() throws Exception {
        reset(restTemplate);
    }

    @Test
    public void testCreateFromProducts() throws Exception {
        List<Clothing> clothing = createClothing();

        tokenService.createFromClothing(clothing);

        verify(restTemplate).postForEntity(generateFullPostUrl(), clothing, Clothing.class);
    }

    private String generateFullPostUrl() {
        return autocompleteURL + tokenCreationEndpoint;
    }
}