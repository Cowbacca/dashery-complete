package uk.co.dashery.ingestor.csv;

import com.univocity.parsers.common.processor.RowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import uk.co.dashery.ingestor.Product;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public abstract class ProductCsvParser<T extends RowProcessor> {
    public List<Product> parse(Reader csvReader) throws IOException {
        T rowProcessor = getRowProcessor();

        CsvParser parser = createCsvParser(rowProcessor);

        parser.parse(csvReader);

        return getProducts(rowProcessor);
    }

    protected abstract T getRowProcessor();

    private CsvParser createCsvParser(RowProcessor rowProcessor) {
        CsvParserSettings parserSettings = createCsvParserSettings(rowProcessor);

        return new CsvParser(parserSettings);
    }

    private CsvParserSettings createCsvParserSettings(RowProcessor rowProcessor) {
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        parserSettings.setLineSeparatorDetectionEnabled(true);
        return parserSettings;
    }

    protected abstract List<Product> getProducts(T rowProcessor);
}
