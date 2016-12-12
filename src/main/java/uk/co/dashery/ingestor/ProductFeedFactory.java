package uk.co.dashery.ingestor;

import org.springframework.stereotype.Component;
import uk.co.dashery.ingestor.csv.AffiliateWindowProductCsvParser;
import uk.co.dashery.ingestor.csv.DasheryProductCsvParser;
import uk.co.dashery.ingestor.csv.ProductCsvParser;

import javax.inject.Inject;
import java.io.IOException;

@Component
class ProductFeedFactory {

    @Inject
    private DasheryProductCsvParser dasheryClothingCsvParser;
    @Inject
    private AffiliateWindowProductCsvParser affiliateWindowProductCsvParser;

    public ProductFeed create(ProductFeedForm productFeedForm) throws IOException {
        return new ProductFeed(productFeedForm.generateReader(), getClothingCsvParser
                (productFeedForm));
    }

    private ProductCsvParser getClothingCsvParser(ProductFeedForm productFeedForm) {
        if (productFeedForm.isAffiliateWindowFormat()) {
            return affiliateWindowProductCsvParser;
        } else {
            return dasheryClothingCsvParser;
        }
    }
}
