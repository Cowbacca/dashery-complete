package uk.co.dashery.clothingquery;

import uk.co.dashery.clothingquery.clothing.Clothing;

import java.util.ArrayList;
import java.util.List;

public class ClothingTestUtils {

    public static List<Clothing> createClothing() {
        List<Clothing> clothing = new ArrayList<>();
        clothing.add(generateClothing("id123", "Test Brand", "Test Item", 100, "A Tag", "Another " +
                "Tag"));
        clothing.add(generateClothing("id456", "Another Day", "Another Dollar", 200, "Different " +
                "Tag"));
        return clothing;
    }

    public static Clothing generateClothing(String id, String brand, String name, int price,
                                            String... tags) {
        Clothing clothing = new Clothing(id);
        clothing.setBrand(brand);
        clothing.setName(name);
        clothing.setPrice(price);
        clothing.setSearchableText(String.join(" ", tags));
        return clothing;
    }

    public static Clothing[] expectedClothing() {
        Clothing firstClothing = new Clothing("id123", "A Test Brand", "Test Item", 10000,
                "a_link.html", "image.jpg", "Some description or other");
        Clothing secondClothing = new Clothing("id456", "Another Day", "Another Dollar", 200,
                "different_link", "image2.jpg", "A different description");
        return new Clothing[]{firstClothing, secondClothing};
    }

}
