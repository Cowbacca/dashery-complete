package uk.co.dashery.indexer;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uk.co.dashery.common.ProductsCreatedEvent;
import uk.co.dashery.ingestor.productfeed.Product;

import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.MockitoAnnotations.initMocks;

public class IndexerControllerTest {

    private static final String MERCHANT = "Some Brand";
    @Mock
    private ClothingIndexRepository clothingIndexRepository;
    @InjectMocks
    private IndexerController indexerController;

    @Mock
    private Product product;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testHandleProductsCreatedEvent() throws Exception {
        List<Product> products = Lists.newArrayList(product);

        indexerController.handleProductsCreatedEvent(new ProductsCreatedEvent(products, MERCHANT));

        InOrder inOrder = inOrder(clothingIndexRepository);
        inOrder.verify(clothingIndexRepository).deleteByBrand(MERCHANT);
        inOrder.verify(clothingIndexRepository).save(products);
    }
}