package uk.co.dashery.clothingquery.clothing;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ClothingRepositoryImpl implements ClothingRepositoryCustom {
    @Inject
    private MongoOperations mongoOperations;

    @Override
    public List<Clothing> findByAllTagsIn(String... tags) {
        Criteria[] whereTagsInTagsValues = Arrays.stream(tags)
                .map(tag -> new Criteria("tags.value").is(tag))
                .toArray(size -> new Criteria[size]);
        Criteria whereAllOfTheGivenTags = new Criteria().andOperator(whereTagsInTagsValues);
        Query query = new Query(whereAllOfTheGivenTags);
        return mongoOperations.find(query, Clothing.class);
    }
}
