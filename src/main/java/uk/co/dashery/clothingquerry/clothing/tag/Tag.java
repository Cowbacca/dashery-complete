package uk.co.dashery.clothingquerry.clothing.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private String value;
    private int score;

    public void addToScore(int toAdd) {
        score += toAdd;
    }
}
