package uk.co.dashery.clothing.clothing;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;
import uk.co.dashery.clothing.Clothing;
import uk.co.dashery.clothing.ClothingController;
import uk.co.dashery.clothing.ClothingRepository;
import uk.co.dashery.clothing.ProductToClothingConverter;
import uk.co.dashery.ingestor.productfeed.Product;
import uk.co.dashery.ingestor.productfeed.ProductsCreatedEvent;

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

    @Spy
    private ProductToClothingConverter productToClothingConverter = new ProductToClothingConverter();
    @InjectMocks
    private ClothingController clothingController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testProcessesNewClothing() {
        clothingController.handleProductsCreated(getProductsCreatedEvent("id123"));

        Clothing clothingItem = new Clothing("id123");
        clothingItem.setBrand(MERCHANT);
        List<Clothing> expectedNewClothing = Lists.newArrayList(clothingItem);

        verify(mockClothingRepository).deleteByBrand(MERCHANT);
        verify(mockClothingRepository).save(expectedNewClothing);
    }

    @Test
    public void testDoesNothingIfProductsCreatedEventContainsNoProducts() {
        clothingController.handleProductsCreated(new ProductsCreatedEvent(new ArrayList<>(), MERCHANT));

        verifyZeroInteractions(mockClothingRepository, mockApplicationEventPublisher);
    }

    private ProductsCreatedEvent getProductsCreatedEvent(String id) {
        Product product = new Product();
        product.setId(id);
        product.setMerchant(MERCHANT);
        return new ProductsCreatedEvent(Lists.newArrayList(product), MERCHANT);
    }
}