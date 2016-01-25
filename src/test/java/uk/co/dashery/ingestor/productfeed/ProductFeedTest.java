package uk.co.dashery.ingestor.productfeed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import uk.co.dashery.ingestor.productfeed.csv.AffiliateWindowProductCsvParser;
import uk.co.dashery.ingestor.productfeed.csv.CsvFormatException;
import uk.co.dashery.ingestor.productfeed.csv.DasheryProductCsvParser;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.ingestor.productfeed.ProductFeedUtils.expectedProducts;
import static uk.co.dashery.ingestor.productfeed.ProductFeedUtils.generateCsvFile;

public class ProductFeedTest {

    @Spy
    private DasheryProductCsvParser dasheryClothingCsvParser = new DasheryProductCsvParser();
    @Spy
    private AffiliateWindowProductCsvParser affiliateWindowProductCsvParser = new
            AffiliateWindowProductCsvParser();

    @InjectMocks
    private ProductFeedFactory productFeedFactory;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testParsesCsvInDasheryFormat() throws Exception {
        ProductFeed productFeed = productFeedFactory.create(new ProductFeedForm(generateCsvFile
                ("test.csv"), false));

        List<Product> products = productFeed.getProducts();

        assertThat(products, is(expectedProducts()));
    }

    @Test
    public void testParsesCsvInAffiliateWindowFormat() throws IOException {
        ProductFeed productFeed = productFeedFactory.create(new ProductFeedForm(generateCsvFile
                ("affiliatewindow.csv"),
                true));

        List<Product> products = productFeed.getProducts();

        assertThat(products, is(expectedProducts()));
    }

    @Test(expected = CsvFormatException.class)
    public void testGivesAnErrorWhenRequiredFieldsAreNotPresentInAffiliateWindowCsv() throws
            IOException {
        ProductFeed productFeed = productFeedFactory.create(
                new ProductFeedForm(generateCsvFile("affiliatewindow-no-brand.csv"), true));

        productFeed.getProducts();
    }
}