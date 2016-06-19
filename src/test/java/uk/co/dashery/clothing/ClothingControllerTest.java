package uk.co.dashery.clothing;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import uk.co.dashery.clothing.image.ImageTransformer;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.common.ProductFeedIngestedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClothingControllerTest {
    public static final String MERCHANT = "Merctest";
    public static final String IMAGE_LINK = "imageLink";
    public static final String TRANSFORMED_URL = "transformedUrl";
    public static final String ID = "id123";

    @Mock
    private ClothingRepository mockClothingRepository;
    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;
    @Mock
    private ImageTransformer imageTransformer;

    @InjectMocks
    private ClothingController clothingController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testProcessesNewClothing() {
        when(imageTransformer.transformedUrl(anyString(), eq(IMAGE_LINK))).thenReturn(Optional.of(TRANSFORMED_URL));

        clothingController.handleProductFeedIngested(getProductsCreatedEvent(ID));

        ClothingItem clothingItem = new ClothingItem(ID);
        clothingItem.setBrand(MERCHANT);
        clothingItem.setImageLink(TRANSFORMED_URL);
        Set<ClothingItem> expectedNewClothing = Sets.newHashSet(clothingItem);

        verify(mockClothingRepository).deleteByBrand(MERCHANT);
        verify(mockClothingRepository).save(expectedNewClothing);
    }

    @Test
    public void testDoesNothingIfProductsCreatedEventContainsNoProducts() {
        clothingController.handleProductFeedIngested(new ProductFeedIngestedEvent(MERCHANT, new ArrayList<>()));

        verifyZeroInteractions(mockClothingRepository, mockApplicationEventPublisher);
    }

    @Test
    public void testAppliesImageTransformationsToClothingImages() {
        ClothingItem clothingItem = new ClothingItem();
        clothingItem.setImageLink(IMAGE_LINK);
        ProductFeedIngestedEvent productFeedIngestedEvent = new ProductFeedIngestedEvent(MERCHANT, Lists.newArrayList(clothingItem));

        when(imageTransformer.transformedUrl(anyString(), eq(IMAGE_LINK))).thenReturn(Optional.of(TRANSFORMED_URL));

        clothingController.handleProductFeedIngested(productFeedIngestedEvent);

        ClothingItem transformedClothingItem = new ClothingItem();
        transformedClothingItem.setImageLink(TRANSFORMED_URL);
        verify(mockClothingRepository).save(Sets.newHashSet(transformedClothingItem));
    }

    @Test
    public void testDoesNotSaveClothingWhichHasFailedImageConversion() {
        when(imageTransformer.transformedUrl(ID + "-" + MERCHANT, IMAGE_LINK)).thenReturn(Optional.empty());

        clothingController.handleProductFeedIngested(getProductsCreatedEvent(ID));

        verify(mockClothingRepository).save(Collections.emptySet());
    }

    private ProductFeedIngestedEvent getProductsCreatedEvent(String id) {
        ClothingItem clothingItem = new ClothingItem();
        clothingItem.setId(id);
        clothingItem.setBrand(MERCHANT);
        clothingItem.setImageLink(IMAGE_LINK);
        return new ProductFeedIngestedEvent(MERCHANT, Lists.newArrayList(clothingItem));
    }
}