//package com.lawrencejews.orderservice.service;
//
//import com.lawrencejews.orderservice.entity.Order;
//import com.lawrencejews.orderservice.exception.CustomException;
//import com.lawrencejews.orderservice.external.client.PaymentService;
//import com.lawrencejews.orderservice.external.client.ProductService;
//import com.lawrencejews.orderservice.external.client.response.PaymentResponse;
//import com.lawrencejews.orderservice.models.OrderResponse;
//import com.lawrencejews.orderservice.models.PaymentMode;
//import com.lawrencejews.orderservice.repository.OrderRepository;
//import com.lawrencejews.productservice.models.ProductResponse;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.Instant;
//import java.util.Optional;
//
//@SpringBootTest
//public class OrderServiceImplTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//    @Mock
//    private ProductService productService;
//    @Mock
//    private PaymentService paymentService;
//    @Mock
//    private RestTemplate restTemplate;
//    @InjectMocks
//    OrderService orderService = new OrderServiceImpl();
//
//    @DisplayName("Get Order - Success Scenario")
//    @Test
//    void test_When_Order_Success(){
//
//        // Mocking
//        Order order = getMockOrder();
//        Mockito.when(orderRepository.findById(ArgumentMatchers.anyLong()))
//                .thenReturn(Optional.of(order));
//
//        Mockito.when(restTemplate.getForObject(
//                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
//                ProductResponse.class
//        )).thenReturn(getMockProductResponse());
//
//        Mockito.when(restTemplate.getForObject(
//                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
//                PaymentResponse.class
//        )).thenReturn(getMockPaymentResponse());
//
//        // Actual Method Calls
//        OrderResponse orderResponse = orderService.getOrderDetails(1);
//
//        // Verification of Calls
//        Mockito.verify(orderRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
//        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(
//                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
//                ProductResponse.class
//        );
//        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(
//                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
//                PaymentResponse.class
//        );
//
//        // Assert Result  - What Object
//        Assertions.assertNotNull(orderResponse);
//        Assertions.assertEquals(order.getId(), orderResponse.getOrderId());
//
//    }
//
//    // Exception Test or Error Testing
//    @DisplayName("Get Orders - Failure Scenario")
//    @Test
//    void test_When_Get_Order_NOT_FOUND_then_not_Found(){
//
//        Mockito.when(orderRepository.findById(ArgumentMatchers.anyLong()))
//                .thenReturn(Optional.ofNullable(null));
//
//        CustomException exception = Assertions.assertThrows(CustomException.class,
//                () -> orderService.getOrderDetails(1));
//        Assertions.assertEquals("NOT_FOUND", exception.getErrorCode());
//        Assertions.assertEquals(404, exception.getStatus());
//        Mockito.verify(orderRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
//
//    }
//
//    // Dummy Product for the Mocking
//    private Order getMockOrder() {
//        return Order.builder()
//                .orderStatus("PLACED")
//                .orderDate(Instant.now())
//                .id(1)
//                .amount(100)
//                .quantity(200)
//                .productId(2)
//                .build();
//    }
//
//    private ProductResponse getMockProductResponse(){
//        return ProductResponse.builder()
//                .productId(2)
//                .productName("iPhone")
//                .price(100)
//                .quantity(200)
//                .build();
//    };
//
//    private PaymentResponse getMockPaymentResponse() {
//        return PaymentResponse.builder()
//                .paymentId(1)
//                .paymentDate(Instant.now())
//                .paymentMode(PaymentMode.CASH)
//                .amount(200)
//                .orderId(1)
//                .status("ACCEPTED")
//                .build();
//    }
//}