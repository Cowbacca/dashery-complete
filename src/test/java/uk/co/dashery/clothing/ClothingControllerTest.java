package uk.co.dashery.clothing;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.common.ProductFeedIngestedEvent;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClothingControllerTest {
    public static final String MERCHANT = "Merctest";

    @Mock
    private ClothingRepository mockClothingRepository;
    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;

    @InjectMocks
    private ClothingController clothingController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testProcessesNewClothing() {
        clothingController.handleProductFeedIngested(getProductsCreatedEvent("id123"));

        ClothingItem clothingItem = new ClothingItem("id123");
        clothingItem.setBrand(MERCHANT);
        List<ClothingItem> expectedNewClothing = Lists.newArrayList(clothingItem);

        verify(mockClothingRepository).deleteByBrand(MERCHANT);
        verify(mockClothingRepository).save(expectedNewClothing);
    }

    @Test
    public void testDoesNothingIfProductsCreatedEventContainsNoProducts() {
        clothingController.handleProductFeedIngested(new ProductFeedIngestedEvent(MERCHANT, new ArrayList<>()));

        verifyZeroInteractions(mockClothingRepository, mockApplicationEventPublisher);
    }

    private ProductFeedIngestedEvent getProductsCreatedEvent(String id) {
        ClothingItem clothingItem = new ClothingItem();
        clothingItem.setId(id);
        clothingItem.setBrand(MERCHANT);
        return new ProductFeedIngestedEvent(MERCHANT, Lists.newArrayList(clothingItem));
    }
}