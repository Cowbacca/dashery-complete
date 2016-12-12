package uk.co.dashery.common;

import lombok.Value;

import java.util.Set;

@Value
public class ClothingItemsPersistedEvent {
    private final String brand;
    private final Set<ClothingItem> clothingItems;
}
