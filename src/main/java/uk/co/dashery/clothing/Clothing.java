package uk.co.dashery.clothing;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Clothing {

    @Id
    private String id;
    private String brand;
    private String name;
    private int price;
    private String link;
    private String imageLink;
    private String searchableText;

    public Clothing() {
        this(null, null, null, 0, null, null, (String) null);
    }

    public Clothing(String id) {
        this(id, null, null, 0, null, null, (String) null);
    }

    public Clothing(String id, String brand, String name, int price, String link, String imageLink,
                    String searchableText) {
        setId(id);
        setBrand(brand);
        setName(name);
        setPrice(price);
        setLink(link);
        setImageLink(imageLink);
        setSearchableText(searchableText);
    }

    @PersistenceConstructor
    protected Clothing(String id, String brand, String name, int price, String link, String
            imageLink) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.link = link;
        this.imageLink = imageLink;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("brand")
    public String getBrand() {
        return brand;
    }

    @JsonProperty("merchant")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public void setSearchableText(String description) {
        this.searchableText = description;
    }
}
