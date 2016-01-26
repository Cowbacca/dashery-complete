package uk.co.dashery.frontend;


import org.springframework.stereotype.Component;
import uk.co.dashery.clothingquery.clothing.Clothing;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FrontEndClothingConverter {

    public List<FrontEndClothing> convert(List<Clothing> clothingList) {
        return clothingList.stream().map(this::convert).collect(Collectors.toList());
    }

    private FrontEndClothing convert(Clothing clothing) {
        FrontEndClothing frontEndClothing = new FrontEndClothing();
        frontEndClothing.setBrand(clothing.getBrand());
        frontEndClothing.setName(clothing.getName());
        frontEndClothing.setPrice(clothing.getPrice());
        frontEndClothing.setLink(clothing.getLink());
        frontEndClothing.setImageLink(clothing.getImageLink());
        return frontEndClothing;
    }
}
