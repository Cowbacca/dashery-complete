package uk.co.dashery.autocomplete.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.co.dashery.autocomplete.data.Token;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

}
