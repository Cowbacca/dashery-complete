package uk.co.dashery.common;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.dashery.clothing.image.ImageTransformer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ClothingItem {
    @Parsed
    private String id;
    @Parsed
    private String brand;
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

    public ClothingItem(String id) {
        this();
        setId(id);
    }

    @PersistenceConstructor
    public ClothingItem(String id, String brand, String name, int price, String link, String
            imageLink, String description) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.link = link;
        this.imageLink = imageLink;
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject()
                    .put("objectID", compositeId())
                    .put("brand", brand)
                    .put("name", name)
                    .put("price", price)
                    .put("link", link)
                    .put("imageLink", imageLink)
                    .put("description", description);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String compositeId() {
        String compositeId = id + "-" + brand;
        return compositeId.replace(" ", "");
    }

    public void transformImage(ImageTransformer imageTransformer) {
        imageLink = imageTransformer.transformedUrl(compositeId(), imageLink);
    }
}
