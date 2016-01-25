package uk.co.dashery.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clothing {
    public String brand;
    public String name;
    public String link;
    public String imageLink;
    public int price;
}
