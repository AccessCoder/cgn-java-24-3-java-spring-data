package com.example.springdata.shop;

import com.example.springdata.shop.order.Order;
import com.example.springdata.shop.order.OrderRepo;
import com.example.springdata.shop.order.OrderStatus;
import com.example.springdata.shop.product.Product;
import com.example.springdata.shop.product.ProductNotAvailableException;
import com.example.springdata.shop.product.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo;
    private final UtilService utilService;

    public Order addOrder(List<String> productIds) throws ProductNotAvailableException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(ProductNotAvailableException::new);
            products.add(productToOrder);
        }

        Order newOrder = new Order(utilService.generateRandomId(), products, OrderStatus.PROCESSING, utilService.generateTimestamp());

        return orderRepo.save(newOrder);
    }

    public List<Order> findAllOrders(OrderStatus status) {
        return orderRepo.findAllByStatus(status);
    }

    public void updateOrder(String orderId, OrderStatus newStatus) {
        Order oldOrder = orderRepo.findById(orderId).orElseThrow();
        orderRepo.deleteById(orderId);
        Order newOrder = oldOrder.withStatus(newStatus);
        orderRepo.save(newOrder);
    }
}
