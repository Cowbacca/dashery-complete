package uk.co.dashery.autocomplete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Comparable<Token> {

    @Id
    private String value;

    @Override
    public int compareTo(Token otherToken) {
        if (value.length() != otherToken.getValue().length()) {
            return value.length() - otherToken.getValue().length();
        } else {
            return value.compareTo(otherToken.getValue());
        }
    }
}
