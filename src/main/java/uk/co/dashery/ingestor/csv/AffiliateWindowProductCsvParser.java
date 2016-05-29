package uk.co.dashery.ingestor.csv;

import com.univocity.parsers.common.processor.RowListProcessor;
import org.springframework.stereotype.Component;
import uk.co.dashery.common.ClothingItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AffiliateWindowProductCsvParser extends ProductCsvParser<RowListProcessor> {

    @Override
    protected RowListProcessor getRowProcessor() {
        return new RowListProcessor();
    }

    private final Map<String, BiConsumer<ClothingItem, String>> nameToConversionMap;

    public AffiliateWindowProductCsvParser() {
        nameToConversionMap = new HashMap<>();
        nameToConversionMap.put("search_price", (clothing, price) -> clothing.setPrice(Integer
                .parseInt(price.replace(".", ""))));
        nameToConversionMap.put("description", ClothingItem::setDescription);
        nameToConversionMap.put("merchant_name", ClothingItem::setBrand);
        nameToConversionMap.put("product_name", ClothingItem::setName);
        nameToConversionMap.put("aw_deep_link", ClothingItem::setLink);
        nameToConversionMap.put("merchant_image_url", ClothingItem::setImageLink);
        nameToConversionMap.put("merchant_product_id", ClothingItem::setId);
    }

    @Override
    protected List<ClothingItem> getProducts(RowListProcessor rowProcessor) {
        Map<String, Integer> nameToIndexMap = generateNameToIndexMap(rowProcessor);

        return generateProductsFromRows(rowProcessor, nameToIndexMap);
    }

    private Map<String, Integer> generateNameToIndexMap(RowListProcessor rowProcessor) {
        String[] headers = rowProcessor.getHeaders();
        return IntStream.range(0, headers.length)
                .boxed()
                .collect(Collectors.toMap(i -> headers[i], i -> i));
    }

    private List<ClothingItem> generateProductsFromRows(RowListProcessor rowProcessor, Map<String,
            Integer> nameToIndexMap) {
        return rowProcessor.getRows()
                .stream()
                .map(row -> {
                    ClothingItem clothing = new ClothingItem();
                    nameToConversionMap.forEach((name, conversion) -> {
                        Integer index = getIndex(nameToIndexMap, name);
                        conversion.accept(clothing, row[index]);
                    });
                    return clothing;
                })
                .collect(Collectors.toList());
    }

    private Integer getIndex(Map<String, Integer> nameToIndexMap, String name) {
        Integer index = nameToIndexMap.get(name);
        if (index == null) {
            throw new CsvFormatException("Column " + name + " must be present in CSV file.");
        }
        return index;
    }
}
