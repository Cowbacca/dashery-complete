package uk.co.dashery.ingestor.csv;

public class CsvFormatException extends RuntimeException {
    CsvFormatException(String message) {
        super(message);
    }
}
