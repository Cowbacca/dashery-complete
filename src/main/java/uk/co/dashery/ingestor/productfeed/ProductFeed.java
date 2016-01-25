package uk.co.dashery.ingestor.productfeed;

import uk.co.dashery.ingestor.productfeed.csv.ProductCsvParser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class ProductFeed {
    private final Reader reader;
    private final ProductCsvParser productCsvParser;

    public ProductFeed(Reader reader, ProductCsvParser productCsvParser) {
        this.reader = reader;
        this.productCsvParser = productCsvParser;
    }

    public List<Product> getProducts() throws IOException {
        return productCsvParser.parse(reader);
    }
}
