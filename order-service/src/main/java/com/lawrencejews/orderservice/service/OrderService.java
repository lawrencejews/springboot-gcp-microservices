package com.lawrencejews.orderservice.service;

import com.lawrencejews.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
