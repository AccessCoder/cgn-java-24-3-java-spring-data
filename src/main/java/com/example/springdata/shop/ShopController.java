package com.example.springdata.shop;

import com.example.springdata.shop.order.Order;
import com.example.springdata.shop.order.OrderDTO;
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

    private final ShopService service;

    @PostMapping("/orders")
    public Order addOrder(@RequestBody List<String> productIds) throws ProductNotAvailableException {
        return service.addOrder(productIds);
    }

    @GetMapping("/orders")
    public List<Order> findAllOrders(@RequestParam OrderStatus status) {
        return service.findAllOrders(status);
    }

    @PutMapping("/orders/{orderId}")
    public void updateOrder(@PathVariable String orderId, @RequestParam OrderStatus newStatus) {
        service.updateOrder(orderId, newStatus);
    }

    @PostMapping("/order")
    public Order addOrder2(@RequestBody OrderDTO order) throws ProductNotAvailableException {
        return null;
    }
}
