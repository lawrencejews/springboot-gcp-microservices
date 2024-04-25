package com.lawrencejews.orderservice.service;

import com.lawrencejews.orderservice.entity.Order;
import com.lawrencejews.orderservice.external.client.ProductService;
import com.lawrencejews.orderservice.model.OrderRequest;
import com.lawrencejews.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        // Order Entity -> Save the Data with Status Order Created.
        log.info("Placing Order Request: {}", orderRequest);

        // Product Service -> Block Products(Reduce the Quantity)
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("Order Placed Successfully with Order Id: {}", order.getId());

        return order.getId();

        //Payment Service -> Payments -> Success -> COMPLETE, Else

        // CANCELLED

    }
}
