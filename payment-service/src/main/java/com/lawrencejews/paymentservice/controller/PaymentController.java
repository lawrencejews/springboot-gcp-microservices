package com.lawrencejews.paymentservice.controller;

import com.lawrencejews.paymentservice.model.PaymentRequest;
import com.lawrencejews.paymentservice.model.PaymentResponse;
import com.lawrencejews.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        return  new ResponseEntity<>(
                paymentService.doPayment(paymentRequest), HttpStatus.OK
        );
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse>getPaymentDetailsByOrderId(@PathVariable String orderId){

        return new ResponseEntity<>(
              paymentService.getPaymentDetailsByOrderId(orderId),
                HttpStatus.OK
        );
    }
}
