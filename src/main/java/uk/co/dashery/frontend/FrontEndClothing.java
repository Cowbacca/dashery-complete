package uk.co.dashery.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FrontEndClothing {
    private String brand;
    private String name;
    private String link;
    private String imageLink;
    private int price;
}
