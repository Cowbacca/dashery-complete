package uk.co.dashery.ingestor;

import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.ingestor.csv.ProductCsvParser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

class ProductFeed {
    private final Reader reader;
    private final ProductCsvParser productCsvParser;

    ProductFeed(Reader reader, ProductCsvParser productCsvParser) {
        this.reader = reader;
        this.productCsvParser = productCsvParser;
    }

    public List<ClothingItem> getClothingItems() throws IOException {
        return productCsvParser.parse(reader);
    }
}
