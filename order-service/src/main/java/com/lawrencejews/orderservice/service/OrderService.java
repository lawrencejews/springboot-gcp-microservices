package com.lawrencejews.orderservice.service;

import com.lawrencejews.orderservice.model.OrderRequest;
import com.lawrencejews.orderservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
