package uk.co.dashery.ingestor.productfeed.csv;

public class CsvFormatException extends RuntimeException {
    public CsvFormatException(String message) {
        super(message);
    }
}
