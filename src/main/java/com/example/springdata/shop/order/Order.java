package com.example.springdata.shop.order;

import com.example.springdata.shop.product.Product;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@With
@Document("Orders") //Ändert Namen der Collection in der MongoDB
public record Order(
        String id,
        List<Product> products,
        OrderStatus status,
        Instant orderTime
) {
}
