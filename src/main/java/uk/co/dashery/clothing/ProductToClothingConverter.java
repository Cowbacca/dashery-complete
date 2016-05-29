package uk.co.dashery.clothing;

import org.springframework.stereotype.Component;
import uk.co.dashery.ingestor.productfeed.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductToClothingConverter {
    public List<Clothing> convert(List<Product> products) {
        return products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Clothing convert(Product product) {
        Clothing clothing = new Clothing();
        clothing.setId(product.getId());
        clothing.setBrand(product.getMerchant());
        clothing.setName(product.getName());
        clothing.setPrice(product.getPrice());
        clothing.setLink(product.getLink());
        clothing.setImageLink(product.getImageLink());
        clothing.setSearchableText(product.getDescription());
        return clothing;
    }
}