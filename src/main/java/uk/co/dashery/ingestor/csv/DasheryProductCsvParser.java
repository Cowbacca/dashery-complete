package uk.co.dashery.ingestor.csv;

import com.univocity.parsers.common.processor.BeanListProcessor;
import org.springframework.stereotype.Component;
import uk.co.dashery.common.ClothingItem;

import java.util.List;

@Component
public class DasheryProductCsvParser extends ProductCsvParser<BeanListProcessor<ClothingItem>> {

    @Override
    protected BeanListProcessor<ClothingItem> getRowProcessor() {
        return new BeanListProcessor<>(ClothingItem.class);
    }


    @Override
    protected List<ClothingItem> getProducts(BeanListProcessor<ClothingItem> rowProcessor) {
        return rowProcessor.getBeans();
    }

}
