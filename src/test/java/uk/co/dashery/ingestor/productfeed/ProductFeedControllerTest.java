package uk.co.dashery.ingestor.productfeed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.ui.ExtendedModelMap;
import uk.co.dashery.ingestor.productfeed.csv.AffiliateWindowProductCsvParser;
import uk.co.dashery.ingestor.productfeed.csv.DasheryProductCsvParser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.ingestor.productfeed.ProductFeedUtils.expectedProducts;
import static uk.co.dashery.ingestor.productfeed.ProductFeedUtils.generateCsvFile;

public class ProductFeedControllerTest {

    @InjectMocks
    private ProductFeedController productFeedController;

    @Spy
    @InjectMocks
    private ProductFeedFactory productFeedFactory = new ProductFeedFactory();

    @Spy
    private DasheryProductCsvParser dasheryClothingCsvParser = new DasheryProductCsvParser();
    @Spy
    private AffiliateWindowProductCsvParser affiliateWindowProductCsvParser = new
            AffiliateWindowProductCsvParser();


    @Mock
    private AmqpTemplate amqpTemplate;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testIngestsProducts() throws Exception {
        productFeedController.ingestProducts(new ProductFeedForm(generateCsvFile("test.csv")));

        verify(amqpTemplate).convertAndSend("products", expectedProducts());
    }

    @Test
    public void testProductsForm() {
        ExtendedModelMap model = new ExtendedModelMap();
        productFeedController.productsForm(model);

        assertThat(model.containsValue(new ProductFeedForm()), is(true));
    }
}