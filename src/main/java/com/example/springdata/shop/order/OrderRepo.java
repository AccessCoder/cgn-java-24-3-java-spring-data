package com.example.springdata.shop.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepo extends MongoRepository<Order, String> {

    List<Order> findAllByStatus(OrderStatus status);
}
