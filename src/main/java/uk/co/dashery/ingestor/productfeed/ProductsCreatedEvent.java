package uk.co.dashery.ingestor.productfeed;

import lombok.Value;

import java.util.List;


@Value
public class ProductsCreatedEvent {
    private List<Product> products;
    private String brand;
}
