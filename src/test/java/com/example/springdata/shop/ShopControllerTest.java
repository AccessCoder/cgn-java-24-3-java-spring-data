package com.example.springdata.shop;

import com.example.springdata.shop.order.Order;
import com.example.springdata.shop.order.OrderRepo;
import com.example.springdata.shop.order.OrderStatus;
import com.example.springdata.shop.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // Startet Spring Anwendung, um Testanfragen auf das System zu schicken
@AutoConfigureMockMvc // Konfiguriert unseren MockMVC, damit wir direkt testen können!
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ShopControllerTest {

    @Autowired
    private MockMvc mvc; //Simuliert uns gleich Anfragen aus einem Client

    @Autowired
    private OrderRepo repo;

    @Test
    void findAllOrders_shouldReturnEmptyList_whenCalledInitially() throws Exception {
        //GIVEN
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get("/shop/orders?status=PROCESSING"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void findAllOrders_shouldReturnListOfOrder_whenCalledWithIN_DELIVERY() throws Exception {
        //GIVEN
        repo.save(new Order("1", List.of(new Product("1", "Apfel")), OrderStatus.IN_DELIVERY, Instant.now()));
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get("/shop/orders?status=IN_DELIVERY"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                    [
                                        {
                                            "id": "1",
                                            "products": [
                                                            {
                                                                "id": "1",
                                                                "name": "Apfel"
                                                            }
                                                        ],
                                            "status": "IN_DELIVERY"           
                                        }
                                    ]
"""))
                .andExpect(MockMvcResultMatchers.jsonPath("*.orderTime").isNotEmpty());
    }

    @Test
    void addOrder2_shouldReturnEmptyList_becauseCoachHasntCodedItDifferent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/shop/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                          {
                              "id": "1",
                              "products": [
                                                      {
                                                          "id": "1",
                                                          "name": "Birne"
                                                      }
                                                  ],
                                      "status": "PROCESSING"
                          }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

}