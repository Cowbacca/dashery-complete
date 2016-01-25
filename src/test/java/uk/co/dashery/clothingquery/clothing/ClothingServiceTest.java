package uk.co.dashery.clothingquery.clothing;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uk.co.dashery.clothingquery.token.TokenService;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.dashery.clothingquery.ClothingTestUtils.createClothing;

public class ClothingServiceTest {

    public static final String A_TAG = "test";
    public static final String ANOTHER_TAG = "another";
    @InjectMocks
    private ClothingService clothingService;

    @Mock
    private ClothingRepository mockClothingRepository;
    @Mock
    private TokenService tokenService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testSearch() throws Exception {
        List<Clothing> clothing = createClothing();

        when(mockClothingRepository.findByAllTagsIn(A_TAG, ANOTHER_TAG)).thenReturn(clothing);

        String searchString = String.join(ClothingService.SEPARATOR, A_TAG, ANOTHER_TAG);
        assertThat(clothingService.search(searchString), is(clothing));
    }

    @Test
    public void testCreate() {
        List<Clothing> clothing = createClothing();

        clothingService.create(clothing);

        verify(mockClothingRepository).save(clothing);
        verify(tokenService).createFromClothing(clothing);

    }

    @Test
    public void testSortsClothingWithTagInTitleHigher() {
        Clothing clothingWithTagInName = new Clothing("id1");
        clothingWithTagInName.setName("test");

        Clothing clothingWithTagInSearchableText = new Clothing("id2");
        clothingWithTagInSearchableText.setSearchableText("test");

        when(mockClothingRepository.findByAllTagsIn("test")).thenReturn(Lists.newArrayList
                (clothingWithTagInSearchableText, clothingWithTagInName));

        assertThat(clothingService.search("test"), is(Lists.newArrayList(clothingWithTagInName,
                clothingWithTagInSearchableText)));
    }
}