package uk.co.dashery.clothingquerry.clothing;

import java.util.List;

public interface ClothingRepositoryCustom {
    List<Clothing> findByAllTagsIn(String... tags);
}
