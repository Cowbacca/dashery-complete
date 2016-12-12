package uk.co.dashery.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.co.dashery.clothing.image.ImageTransformer;

import java.util.Optional;

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
        ClothingItem clothingItem = givenAClothingItem("id", "brand", "imageLink");
        when(imageTransformer.transformedUrl("id-brand", "imageLink")).thenReturn(Optional.of("transformedUrl"));

        clothingItem.transformImage(imageTransformer);

        assertThat(clothingItem.getImageLink(), is("transformedUrl"));
    }

    @Test
    public void testRemovesSpacesFromCompositeIDBeforeSubmittingToImageTransformer() {
        ClothingItem clothingItem = givenAClothingItem("an id", "a brand", "imageLink");
        when(imageTransformer.transformedUrl("anid-abrand", "imageLink")).thenReturn(Optional.of("transformedUrl"));

        clothingItem.transformImage(imageTransformer);

        assertThat(clothingItem.getImageLink(), is("transformedUrl"));
    }

    @Test
    public void testReplacesAmpersandWithAndInCompositeIDBeforeSubmittingToImageTransformer() {
        ClothingItem clothingItem = givenAClothingItem("an id", "a & brand", "imageLink");
        when(imageTransformer.transformedUrl("anid-aandbrand", "imageLink")).thenReturn(Optional.of("transformedUrl"));

        clothingItem.transformImage(imageTransformer);

        assertThat(clothingItem.getImageLink(), is("transformedUrl"));
    }

    private ClothingItem givenAClothingItem(String id, String brand, String imageLink) {
        ClothingItem clothingItem = new ClothingItem();
        clothingItem.setId(id);
        clothingItem.setBrand(brand);
        clothingItem.setImageLink(imageLink);
        return clothingItem;
    }
}