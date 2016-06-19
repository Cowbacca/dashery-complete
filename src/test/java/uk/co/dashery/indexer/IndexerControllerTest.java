package uk.co.dashery.indexer;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uk.co.dashery.common.ClothingItem;
import uk.co.dashery.common.ClothingItemsPersistedEvent;

import java.util.Set;

import static org.mockito.Mockito.inOrder;
import static org.mockito.MockitoAnnotations.initMocks;

public class IndexerControllerTest {

    private static final String MERCHANT = "Some Brand";
    @Mock
    private ClothingIndexRepository clothingIndexRepository;
    @InjectMocks
    private IndexerController indexerController;

    @Mock
    private ClothingItem clothingItem;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testHandleProductsCreatedEvent() throws Exception {
        Set<ClothingItem> clothingItems = Sets.newHashSet(clothingItem);

        indexerController.handleProductsCreatedEvent(new ClothingItemsPersistedEvent(MERCHANT, clothingItems));

        InOrder inOrder = inOrder(clothingIndexRepository);
        inOrder.verify(clothingIndexRepository).deleteByBrand(MERCHANT);
        inOrder.verify(clothingIndexRepository).save(clothingItems);
    }
}