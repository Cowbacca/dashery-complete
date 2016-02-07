package uk.co.dashery.clothingquery.clothing;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClothingRepository extends MongoRepository<Clothing, String>,
        ClothingRepositoryCustom {
    void deleteByBrand(String brand);
}
