package uk.co.dashery.frontend;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.ui.Model;
import uk.co.dashery.clothingquery.clothing.Clothing;
import uk.co.dashery.clothingquery.clothing.ClothingController;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ResultsControllerTest {

    public static final String SEARCH_STRING = "test";
    public static final String CLOTHING_NAME = "a name";

    @Mock
    private ClothingController clothingController;
    @Spy
    private FrontEndClothingConverter frontEndClothingConverter = new FrontEndClothingConverter();
    @Mock
    private Model mockModel;

    @InjectMocks
    private ResultsController resultsController;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAddsClothingMatchingSearchQueryToModel() {
        withAClothingReturnedFromTheClothingController();

        resultsController.results(SEARCH_STRING, mockModel);

        verifyModelContainsRepresentationOfClothingFromClothingController();
    }

    private void verifyModelContainsRepresentationOfClothingFromClothingController() {
        FrontEndClothing frontEndClothing = new FrontEndClothing();
        frontEndClothing.setName(CLOTHING_NAME);
        verify(mockModel).addAttribute("clothing", Lists.newArrayList(frontEndClothing));
    }

    private void withAClothingReturnedFromTheClothingController() {
        Clothing clothing = new Clothing();
        clothing.setName(CLOTHING_NAME);
        when(clothingController.clothing(SEARCH_STRING)).thenReturn(Lists.newArrayList(clothing));
    }

    @Test
    public void testIgnoresCaseInSearches() {
        withAClothingReturnedFromTheClothingController();

        resultsController.results(SEARCH_STRING.toUpperCase(), mockModel);

        verifyModelContainsRepresentationOfClothingFromClothingController();
    }
}