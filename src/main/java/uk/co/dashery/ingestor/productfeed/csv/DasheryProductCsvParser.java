package uk.co.dashery.ingestor.productfeed.csv;

import com.univocity.parsers.common.processor.BeanListProcessor;
import org.springframework.stereotype.Component;
import uk.co.dashery.ingestor.productfeed.Product;

import java.util.List;

@Component
public class DasheryProductCsvParser extends ProductCsvParser<BeanListProcessor<Product>> {

    @Override
    protected BeanListProcessor<Product> getRowProcessor() {
        return new BeanListProcessor<>(Product.class);
    }


    @Override
    protected List<Product> getProducts(BeanListProcessor<Product> rowProcessor) {
        return rowProcessor.getBeans();
    }

}
