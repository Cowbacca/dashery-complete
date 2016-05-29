package uk.co.dashery.clothing;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClothingRepository extends MongoRepository<Clothing, String> {
    void deleteByBrand(String brand);
}
