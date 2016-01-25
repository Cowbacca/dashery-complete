package uk.co.dashery.ingestor.productfeed;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.dashery.ingestor.productfeed.ProductFeedUtils.generateCsvFile;

public class ProductFeedFormTest {

    public static final String FIRST_FIVE_LETTERS_OF_FILE = "id,me";

    @Test
    public void testGeneratesReaderFromURL() throws IOException {
        ProductFeedForm productFeedForm = new ProductFeedForm(testCsvUrl());

        Reader reader = productFeedForm.generateReader();

        assertThatFirstFiveCharsOfReaderAre(reader, FIRST_FIVE_LETTERS_OF_FILE.toCharArray());
    }

    private String testCsvUrl() {
        return getClass().getClassLoader().getResource("test.csv").toString();
    }

    @Test
    public void testGeneratesReaderFromFile() throws IOException {
        ProductFeedForm productFeedForm = new ProductFeedForm(generateCsvFile("test.csv"));

        Reader reader = productFeedForm.generateReader();

        assertThatFirstFiveCharsOfReaderAre(reader, FIRST_FIVE_LETTERS_OF_FILE.toCharArray());
    }

    private void assertThatFirstFiveCharsOfReaderAre(Reader reader, char[] firstFiveChars) throws
            IOException {
        char[] chars = new char[5];
        reader.read(chars);

        assertThat(chars, is(firstFiveChars));
    }
}
