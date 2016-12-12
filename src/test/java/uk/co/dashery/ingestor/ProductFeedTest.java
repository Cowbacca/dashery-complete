package uk.co.dashery.ingestor;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.ingestor.csv.AffiliateWindowProductCsvParser;
import uk.co.dashery.ingestor.csv.CsvFormatException;
import uk.co.dashery.ingestor.csv.DasheryProductCsvParser;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

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
        ProductFeed productFeed = productFeedFactory.create(new ProductFeedForm(ProductFeedUtils.generateCsvFile
                ("test.csv"), false));

        List<ClothingItem> clothingItems = productFeed.getClothingItems();

        assertThat(clothingItems, Matchers.is(ProductFeedUtils.expectedProducts()));
    }

    @Test
    public void testParsesCsvInAffiliateWindowFormat() throws IOException {
        ProductFeed productFeed = productFeedFactory.create(new ProductFeedForm(ProductFeedUtils.generateCsvFile
                ("affiliatewindow.csv"),
                true));

        List<ClothingItem> clothingItems = productFeed.getClothingItems();

        assertThat(clothingItems, Matchers.is(ProductFeedUtils.expectedProducts()));
    }

    @Test(expected = CsvFormatException.class)
    public void testGivesAnErrorWhenRequiredFieldsAreNotPresentInAffiliateWindowCsv() throws
            IOException {
        ProductFeed productFeed = productFeedFactory.create(
                new ProductFeedForm(ProductFeedUtils.generateCsvFile("affiliatewindow-no-brand.csv"), true));

        productFeed.getClothingItems();
    }
}