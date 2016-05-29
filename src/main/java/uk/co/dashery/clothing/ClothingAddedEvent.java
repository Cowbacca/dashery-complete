package uk.co.dashery.clothing;


import lombok.Value;

import java.util.List;

@Value
public class ClothingAddedEvent {
    private List<Clothing> clothingList;
}
