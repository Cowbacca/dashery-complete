package uk.co.dashery.ingestor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.ExtendedModelMap;
import uk.co.dashery.common.ProductsCreatedEvent;
import uk.co.dashery.ingestor.csv.AffiliateWindowProductCsvParser;
import uk.co.dashery.ingestor.csv.DasheryProductCsvParser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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
    private ApplicationEventPublisher applicationEventPublisher;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testIngestsProducts() throws Exception {
        productFeedController.ingestProducts(new ProductFeedForm(ProductFeedUtils.generateCsvFile("test.csv")));

        verify(applicationEventPublisher).publishEvent(new ProductsCreatedEvent(ProductFeedUtils.expectedProducts(), "A Test Brand"));
    }

    @Test
    public void testProductsForm() {
        ExtendedModelMap model = new ExtendedModelMap();
        productFeedController.productsForm(model);

        assertThat(model.containsValue(new ProductFeedForm()), is(true));
    }
}