package com.lawrencejews.paymentservice.service;

import com.lawrencejews.paymentservice.model.PaymentRequest;
import com.lawrencejews.paymentservice.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

     PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
