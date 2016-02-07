package uk.co.dashery.clothingquery.clothing;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;
import uk.co.dashery.clothingquery.ClothingAddedEvent;
import uk.co.dashery.ingestor.productfeed.Product;
import uk.co.dashery.ingestor.productfeed.ProductsCreatedEvent;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.clothingquery.ClothingTestUtils.createClothing;

public class ClothingControllerTest {

    public static final String SEARCH_STRING = "test:test";

    @Mock
    private ClothingRepository mockClothingRepository;
    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;
    @Mock
    private ClothingService mockClothingService;

    @Spy
    private ProductToClothingConverter productToClothingConverter = new ProductToClothingConverter();
    @InjectMocks
    private ClothingController clothingController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testClothing() throws Exception {
        List<Clothing> clothing = createClothing();
        when(mockClothingService.search(SEARCH_STRING)).thenReturn(clothing);

        assertThat(clothingController.clothing(SEARCH_STRING), is(clothing));
    }

    @Test
    public void testProcessesNewClothing() {
        List<Clothing> newClothing = Lists.newArrayList(new Clothing("id123"));

        clothingController.handleProductsCreated(getProductsCreatedEvent("id123"));

        verify(mockClothingRepository).save(newClothing);
        verify(mockApplicationEventPublisher).publishEvent(new ClothingAddedEvent(newClothing));
    }

    private ProductsCreatedEvent getProductsCreatedEvent(String id) {
        Product product = new Product();
        product.setId(id);
        return new ProductsCreatedEvent(Lists.newArrayList(product));
    }
}