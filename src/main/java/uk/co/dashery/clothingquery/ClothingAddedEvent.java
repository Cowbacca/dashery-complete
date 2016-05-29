package uk.co.dashery.clothingquery;


import lombok.Value;
import uk.co.dashery.clothingquery.clothing.Clothing;

import java.util.List;

@Value
public class ClothingAddedEvent {
    private List<Clothing> clothingList;
}
