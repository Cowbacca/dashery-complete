package uk.co.dashery.ingestor.productfeed;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject toJsonObject() {
        try {
                        return new JSONObject()
                                .put("objectID", id + "-" + merchant)
                                .put("brand", merchant)
                                .put("name", name)
                                .put("price", price)
                                .put("link", link)
                                .put("imageLink", imageLink)
                                .put("description", description);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
    }
}
