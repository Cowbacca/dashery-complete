package uk.co.dashery.clothingquery.clothing;

import com.google.common.collect.Lists;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.dashery.clothingquery.DasheryClothingQueryIntegrationTest;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.dashery.clothingquery.ClothingTestUtils.generateClothing;

@RunWith(SpringJUnit4ClassRunner.class)
@DasheryClothingQueryIntegrationTest
public class ClothingControllerIT {

    @Inject
    private MongoTemplate mongoTemplate;
    @Inject
    private ClothingRepository clothingRepository;
    @Inject
    private ClothingController clothingController;
    private Clothing bananaAppleClothing;
    private Clothing justBananaClothing;

    @Before
    public void setUp() throws Exception {
        bananaAppleClothing = generateClothing("id123", "Test Brand", "Test Name", 100, "Banana",
                "Apple");
        justBananaClothing = generateClothing("id456", "Test Brand", "Test Name", 100, "Banana");
        clothingRepository.insert(Lists.newArrayList(bananaAppleClothing, justBananaClothing));
    }

    @After
    public void tearDown() throws Exception {
        mongoTemplate.getDb().dropDatabase();
    }

    @Test
    public void testSearchForClothing() throws Exception {
        List<Clothing> clothingWithBananaTag = Lists.newArrayList(bananaAppleClothing,
                justBananaClothing);

        assertThat(clothingController.clothing("banana"), is(clothingWithBananaTag));
    }

    @Test
    public void testSearchForClothingWithTwoTags() {
        assertThat(clothingController.clothing("banana,apple"), is(Lists.newArrayList
                (bananaAppleClothing)));
    }

    @Test
    public void testUpdatesClothingRatherThanDuplicates() throws IOException {
        Clothing clothing = givenAClothing();
        clothing.setPrice(1);

        clothingController.processNewClothing(Lists.newArrayList(clothing));

        MatcherAssert.assertThat(firstClothingWithTagNamedSome().getPrice(), CoreMatchers.is(1));

        clothing.setPrice(2);

        clothingController.processNewClothing(Lists.newArrayList(clothing));

        MatcherAssert.assertThat(firstClothingWithTagNamedSome().getPrice(), CoreMatchers.is(2));
    }

    @Test
    public void testFindsClothingWithSearchTermInName() {
        Clothing clothing = new Clothing("someid");
        clothing.setName("Grey Wool Trousers");
        List<Clothing> expectedClothing = Lists.newArrayList(clothing);

        clothingController.processNewClothing(expectedClothing);

        assertThat(clothingController.clothing("grey"), is(expectedClothing));

    }

    private Clothing givenAClothing() {
        Clothing product = new Clothing();
        product.setId("id");
        product.setSearchableText("Some");
        return product;
    }

    private Clothing firstClothingWithTagNamedSome() {
        return clothingController.clothing("some").get(0);
    }
}
