package com.example.springdata.shop;

import com.example.springdata.shop.order.Order;
import com.example.springdata.shop.order.OrderRepo;
import com.example.springdata.shop.order.OrderStatus;
import com.example.springdata.shop.product.Product;
import com.example.springdata.shop.product.ProductNotAvailableException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {

    private final OrderRepo mockRepo = mock(OrderRepo.class);
    private final UtilService mockUtil = mock(UtilService.class);

    @Test
    void addOrder() throws ProductNotAvailableException {
        //GIVEN
        Instant uniTime = Instant.now();
        List<String> productsToOrder = List.of("1", "1");
        Order expected = new Order("1",
                List.of(new Product("1", "Apfel"),
                        new Product("1", "Apfel")),
                OrderStatus.PROCESSING, uniTime);

        ShopService service = new ShopService(mockRepo, mockUtil);
        when(mockUtil.generateRandomId()).thenReturn("1");
        when(mockUtil.generateTimestamp()).thenReturn(uniTime);
        when(mockRepo.save(expected)).thenReturn(expected);

        //WHEN
        Order actual = service.addOrder(productsToOrder);
        //THEN
        assertEquals(expected, actual);
        verify(mockUtil).generateRandomId();
        verify(mockUtil).generateTimestamp();
        verify(mockRepo).save(expected);
    }

    @Test
    void findAllOrders_shouldReturnEmptyList_whenCalledInitially() {
        //GIVEN
        ShopService service = new ShopService(mockRepo, mockUtil);
        when(mockRepo.findAllByStatus(OrderStatus.PROCESSING)).thenReturn(Collections.EMPTY_LIST);
        //WHEN
        List<Order> actual = service.findAllOrders(OrderStatus.PROCESSING);
        //THEN
        assertEquals(Collections.EMPTY_LIST, actual);
        verify(mockRepo).findAllByStatus(OrderStatus.PROCESSING);
    }

    @Test
    void updateOrder() {
    }
}