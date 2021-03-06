package uk.co.dashery.ingestor;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductFeedFormTest {

    public static final String FIRST_FIVE_LETTERS_OF_FILE = "id,br";

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
        ProductFeedForm productFeedForm = new ProductFeedForm(ProductFeedUtils.generateCsvFile("test.csv"));

        Reader reader = productFeedForm.generateReader();

        assertThatFirstFiveCharsOfReaderAre(reader, FIRST_FIVE_LETTERS_OF_FILE.toCharArray());
    }

    private void assertThatFirstFiveCharsOfReaderAre(Reader reader, char[] firstFiveChars) throws
            IOException {
        char[] chars = new char[5];
        reader.read(chars);

        assertThat(chars, is(firstFiveChars));
    }

    @Test
    public void testGeneratesReaderFromZip() throws IOException {
        ProductFeedForm productFeedForm = new ProductFeedForm(ProductFeedUtils.generateCsvFile("test.zip"));

        Reader reader = productFeedForm.generateReader();

        assertThatFirstFiveCharsOfReaderAre(reader, FIRST_FIVE_LETTERS_OF_FILE.toCharArray());
    }
}
