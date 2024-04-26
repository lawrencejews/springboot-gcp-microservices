package com.lawrencejews.orderservice.controller;

import com.lawrencejews.orderservice.model.OrderRequest;
import com.lawrencejews.orderservice.model.OrderResponse;
import com.lawrencejews.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id : {}", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>getOrderDetails(@PathVariable long orderId ){
    OrderResponse orderResponse
            = orderService.getOrderDetails(orderId);
    return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
