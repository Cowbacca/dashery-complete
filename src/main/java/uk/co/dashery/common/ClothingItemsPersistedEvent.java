package uk.co.dashery.common;

import lombok.Value;

import java.util.List;

@Value
public class ClothingItemsPersistedEvent {
    private final String brand;
    private final List<ClothingItem> clothingItems;
}
