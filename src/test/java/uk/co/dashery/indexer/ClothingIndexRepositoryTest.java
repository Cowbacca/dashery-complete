package uk.co.dashery.indexer;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.google.common.collect.Lists;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uk.co.dashery.ingestor.productfeed.Product;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClothingIndexRepositoryTest {

    public static final String BRAND = "Test Merchant";
    public static final String NAME = "Shirt";
    public static final String DESCRIPTION = "A nice shirt.";
    public static final int PRICE = 15000;
    public static final String LINK = "http://www.example.com";
    public static final String IMAGE_LINK = "http://www.example.com/image";
    @Mock
    private Index index;
    @InjectMocks
    private ClothingIndexRepository clothingIndexRepository;
    private Collection<Product> products;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        products = Lists.newArrayList(new Product("1", BRAND, NAME, DESCRIPTION, PRICE, LINK, IMAGE_LINK));
    }

    @Test
    public void testSavesObjectIDToIndex() throws Exception {
        clothingIndexRepository.save(products);

        assertThatPropertyIs("objectID", "1-Test Merchant");
    }

    @Test
    public void testSavesBrandToIndex() throws Exception {
        clothingIndexRepository.save(products);

        assertThatPropertyIs("brand", BRAND);
    }

    @Test
    public void testSavesNameToIndex() throws Exception {
        clothingIndexRepository.save(products);

        assertThatPropertyIs("name", NAME);
    }

    @Test
    public void testSavesDescriptionToIndex() throws Exception {
        clothingIndexRepository.save(products);

        assertThatPropertyIs("description", DESCRIPTION);
    }

    @Test
    public void testSavesPriceToIndex() throws Exception {
        clothingIndexRepository.save(products);

        assertThatPropertyIs("price", PRICE);
    }

    @Test
    public void testSavesLinkToIndex() throws Exception {
        clothingIndexRepository.save(products);

        assertThatPropertyIs("link", LINK);
    }

    @Test
    public void testSavesImageLinkToIndex() throws Exception {
        clothingIndexRepository.save(products);

        assertThatPropertyIs("imageLink", IMAGE_LINK);
    }


    private void assertThatPropertyIs(String property, Object value) throws AlgoliaException, JSONException {
        ArgumentCaptor<List> jsonObjectCaptor = ArgumentCaptor.forClass(List.class);
        verify(index).saveObjects(jsonObjectCaptor.capture());
        JSONObject jsonObject = (JSONObject) jsonObjectCaptor.getValue().get(0);
        assertThat(jsonObject.getString(property), is(value.toString()));
    }

    @Test
    public void testDeleteByBrand() throws Exception {
        clothingIndexRepository.deleteByBrand(BRAND);

        ArgumentCaptor<Query> queryArgumentCaptor = ArgumentCaptor.forClass(Query.class);
        verify(index).deleteByQuery(queryArgumentCaptor.capture());
        Query query = queryArgumentCaptor.getValue();
        assertThat(query.getFacetFilters(), is("brand:Test Merchant"));
    }
}