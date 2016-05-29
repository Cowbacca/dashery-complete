package uk.co.dashery.clothing;


import org.springframework.data.mongodb.repository.MongoRepository;

interface ClothingRepository extends MongoRepository<Clothing, String> {
    void deleteByBrand(String brand);
}
