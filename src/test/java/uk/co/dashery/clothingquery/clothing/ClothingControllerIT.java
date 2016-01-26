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
import uk.co.dashery.ingestor.productfeed.Product;
import uk.co.dashery.ingestor.productfeed.ProductsCreatedEvent;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.dashery.clothingquery.ClothingTestUtils.generateClothing;

@RunWith(SpringJUnit4ClassRunner.class)
@DasheryClothingQueryIntegrationTest
public class ClothingControllerIT {

    public static final String TAG = "some";
    @Inject
    private ProductToClothingConverter productToClothingConverter;

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
        Product product = withAProduct();
        product.setDescription(TAG);
        product.setPrice(1);
        clothingController.handleProductsCreated(getProductsCreatedEvent(product));

        MatcherAssert.assertThat(firstClothingWithTag(TAG).getPrice(), CoreMatchers.is(1));

        product.setPrice(2);

        clothingController.handleProductsCreated(getProductsCreatedEvent(product));

        MatcherAssert.assertThat(firstClothingWithTag(TAG).getPrice(), CoreMatchers.is(2));
    }

    private Product withAProduct() {
        Product product = new Product();
        product.setId("someid");
        return product;
    }

    private ProductsCreatedEvent getProductsCreatedEvent(Product product) {
        return new ProductsCreatedEvent(Lists.newArrayList(product));
    }

    @Test
    public void testFindsClothingWithSearchTermInName() {
        Product product = withAProduct();
        product.setName("Grey Wool Trousers");

        clothingController.handleProductsCreated(getProductsCreatedEvent(product));

        assertThat(clothingController.clothing("grey"), is(getClothing(product)));

    }

    private List<Clothing> getClothing(Product product) {
        return productToClothingConverter.convert(Lists.newArrayList(product));
    }

    private Clothing firstClothingWithTag(String tag) {
        return clothingController.clothing(tag).get(0);
    }
}
