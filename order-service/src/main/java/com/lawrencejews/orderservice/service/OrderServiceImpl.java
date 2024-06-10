package com.lawrencejews.orderservice.service;

import com.lawrencejews.orderservice.entity.Order;
import com.lawrencejews.orderservice.exception.CustomException;
import com.lawrencejews.orderservice.external.client.PaymentService;
import com.lawrencejews.orderservice.external.client.ProductService;
import com.lawrencejews.orderservice.external.client.request.PaymentRequest;
import com.lawrencejews.orderservice.external.client.response.PaymentResponse;
import com.lawrencejews.orderservice.model.OrderRequest;
import com.lawrencejews.orderservice.model.OrderResponse;
import com.lawrencejews.orderservice.repository.OrderRepository;
import com.lawrencejews.productservice.models.ProductResponse;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;

//    @Value("${microservices.product}")
//    private String productServiceUrl;

//    @Value("${microservices.payment}")
//    private String paymentServiceUrl;

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

        //Payment Service -> Payments -> Success -> COMPLETE, Else
        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully. Changing order status to Placed");
            orderStatus = "PLACED";
        }catch (Exception e){
            log.error("Error occurred in payment. Changing order status to payment failed ");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order Placed Successfully with Order Id: {}", order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {

        log.info("Get order details for Order Id: {}", orderId);

        Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new CustomException("Order not found for the orderId: {}" + orderId,
                "NOT_FOUND",
                404
        ));

        // RestTemplate
        log.info("Invoking Product service to fetch the product for id: {}", order.getProductId());
        ProductResponse productResponse
                = restTemplate.getForObject(
                        "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                        // productServiceUrl + order.getProductId(),
                ProductResponse.class
        );

        log.info("Getting Payment information from the payment service ");
        PaymentResponse paymentResponse
                = restTemplate.getForObject(
                        "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                        // paymentServiceUrl "order/" + order.getId(),
                PaymentResponse.class
        );


        OrderResponse.ProductDetails productDetails
                = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        OrderResponse.PaymentDetails paymentDetails
                = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse orderResponse
                = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
        return orderResponse;
    }
}
