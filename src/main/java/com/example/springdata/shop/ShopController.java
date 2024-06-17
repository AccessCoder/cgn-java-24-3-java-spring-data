package com.example.springdata.shop;

import com.example.springdata.shop.order.Order;
import com.example.springdata.shop.order.OrderRepo;
import com.example.springdata.shop.order.OrderStatus;
import com.example.springdata.shop.product.Product;
import com.example.springdata.shop.product.ProductNotAvailableException;
import com.example.springdata.shop.product.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo;


    @PostMapping("/orders")
    public Order addOrder(@RequestBody List<String> productIds) throws ProductNotAvailableException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(ProductNotAvailableException::new);
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.save(newOrder);
    }

    @GetMapping("/orders")
    public List<Order> findAllOrders(@RequestParam OrderStatus status) {
        return orderRepo.findAllByStatus(status);
    }

    @PutMapping("/orders/{orderId}")
    public void updateOrder(@PathVariable String orderId, @RequestParam OrderStatus newStatus) {
        Order oldOrder = orderRepo.findById(orderId).orElseThrow();
        orderRepo.deleteById(orderId);
        Order newOrder = oldOrder.withStatus(newStatus);
        orderRepo.save(newOrder);
    }
}
