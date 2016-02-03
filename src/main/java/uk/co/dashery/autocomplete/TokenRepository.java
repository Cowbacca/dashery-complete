package uk.co.dashery.autocomplete;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    Set<Token> findByValueStartsWith(String beginning);
}
