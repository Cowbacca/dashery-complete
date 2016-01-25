package uk.co.dashery.ingestor.productfeed;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Parsed
    private String id;
    @Parsed
    private String merchant;
    @Parsed
    private String name;
    @Parsed
    private String description;
    @Parsed
    private int price;
    @Parsed
    private String link;
    @Parsed
    private String imageLink;
}
