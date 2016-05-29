package uk.co.dashery.common;

import lombok.Value;
import uk.co.dashery.ingestor.Product;

import java.util.List;


@Value
public class ProductsCreatedEvent {
    private List<Product> products;
    private String brand;
}
