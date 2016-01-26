package uk.co.dashery.frontend;

import com.google.common.collect.Lists;
import org.junit.Test;
import uk.co.dashery.clothingquery.clothing.Clothing;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class FrontEndClothingConverterTest {

    public static final String ID = "id";
    public static final String BRAND = "brand";
    public static final String NAME = "name";
    public static final int PRICE = 10;
    public static final String LINK = "link";
    public static final String IMAGE_LINK = "imageLink";
    public static final String SEARCHABLE_TEXT = "searchableText";

    @Test
    public void testConvertsAClothingQueryClothingIntoAFrontEndClothing() {
        FrontEndClothingConverter frontEndClothingConverter = new FrontEndClothingConverter();

        List<Clothing> clothingList = Lists.newArrayList(
                new Clothing(ID, BRAND, NAME, PRICE, LINK, IMAGE_LINK, SEARCHABLE_TEXT));

        List<FrontEndClothing> frontEndClothingList = frontEndClothingConverter.convert(clothingList);


        assertThat(frontEndClothingList, is(Lists.newArrayList(getPopulatedFrontEndClothing())));
    }

    private FrontEndClothing getPopulatedFrontEndClothing() {
        FrontEndClothing frontEndClothing = new FrontEndClothing();
        frontEndClothing.setBrand(BRAND);
        frontEndClothing.setName(NAME);
        frontEndClothing.setPrice(PRICE);
        frontEndClothing.setLink(LINK);
        frontEndClothing.setImageLink(IMAGE_LINK);
        return frontEndClothing;
    }
}