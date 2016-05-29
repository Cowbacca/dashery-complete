package uk.co.dashery.clothing;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
class Clothing {

    @Id
    private String id;
    private String brand;
    private String name;
    private int price;
    private String link;
    private String imageLink;
    private String searchableText;

    Clothing(String id) {
        this();
        setId(id);
    }

    @PersistenceConstructor
    Clothing(String id, String brand, String name, int price, String link, String
            imageLink, String searchableText) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.link = link;
        this.imageLink = imageLink;
        this.searchableText = searchableText;
    }
}
