package uk.co.dashery.clothingquery.clothing;

import java.util.List;

public interface ClothingRepositoryCustom {
    List<Clothing> findByAllTagsIn(String... tags);
}
