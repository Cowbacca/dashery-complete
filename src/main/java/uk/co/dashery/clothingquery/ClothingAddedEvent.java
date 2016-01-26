package uk.co.dashery.clothingquery;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.dashery.clothingquery.clothing.Clothing;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
public class ClothingAddedEvent {

    @Getter
    private List<Clothing> clothingList;
}
