package uk.co.dashery.clothingquerry.clothing;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClothingRepository extends MongoRepository<Clothing, String>,
        ClothingRepositoryCustom {
}
