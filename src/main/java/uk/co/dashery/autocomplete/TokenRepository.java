package uk.co.dashery.autocomplete;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    Stream<Token> findByValueStartsWith(String beginning);
}
