package uk.co.dashery.clothingquery.clothing;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.clothingquery.ClothingTestUtils.createClothing;

public class ClothingControllerTest {

    public static final String SEARCH_STRING = "test:test";
    @InjectMocks
    private ClothingController clothingController;

    @Mock
    private ClothingService mockClothingService;

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
        ArrayList<Clothing> newClothing = Lists.newArrayList(new Clothing("id123"));
        clothingController.processNewClothing(newClothing);

        verify(mockClothingService).create(newClothing);
    }
}