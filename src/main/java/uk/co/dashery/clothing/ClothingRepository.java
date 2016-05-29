package uk.co.dashery.clothing;


import org.springframework.data.mongodb.repository.MongoRepository;
import uk.co.dashery.common.ClothingItem;

interface ClothingRepository extends MongoRepository<ClothingItem, String> {
    void deleteByBrand(String brand);
}
