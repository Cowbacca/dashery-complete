package uk.co.dashery.ingestor;


import com.google.common.collect.Lists;
import org.springframework.mock.web.MockMultipartFile;
import uk.co.dashery.common.ClothingItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProductFeedUtils {
    public static List<ClothingItem> expectedProducts() {
        return Lists.newArrayList(
                new ClothingItem("id123", "A Test Brand", "Test Item", "Some description or other.",
                        10000, "a_link.html", "image.jpg"),
                new ClothingItem("id456", "A Test Brand", "Another Dollar", "A different description.",
                        200, "different_link",
                        "image2.jpg"));
    }


    public static InputStream getTestCsvAsStream(String filename) {
        return ProductFeedUtils.class.getClassLoader().getResourceAsStream(filename);
    }

    public static MockMultipartFile generateCsvFile(String filename) throws IOException {
        InputStream inputFile = getTestCsvAsStream(filename);
        return new MockMultipartFile("csvFile", filename, "multipart/form-data", inputFile);
    }

}
