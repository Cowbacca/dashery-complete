package uk.co.dashery.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.co.dashery.clothing.image.ImageTransformer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClothingItemTest {

    @Mock
    private ImageTransformer imageTransformer;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testChangesImageLinkToTransformedImageLinkOnTransformImage() {
        ClothingItem clothingItem = givenAClothingItem();
        when(imageTransformer.transformedUrl("id-brand", "imageLink")).thenReturn("transformedUrl");

        clothingItem.transformImage(imageTransformer);

        assertThat(clothingItem.getImageLink(), is("transformedUrl"));
    }

    private ClothingItem givenAClothingItem() {
        ClothingItem clothingItem = new ClothingItem();
        clothingItem.setId("id");
        clothingItem.setBrand("brand");
        clothingItem.setImageLink("imageLink");
        return clothingItem;
    }
}