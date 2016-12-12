package uk.co.dashery.common;

import lombok.Value;

import java.util.List;


@Value
public class ProductFeedIngestedEvent {
    private String brand;
    private List<ClothingItem> clothingItems;
}
