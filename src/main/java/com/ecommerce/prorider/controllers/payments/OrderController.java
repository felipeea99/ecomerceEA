package com.ecommerce.prorider.controllers.payments;

import com.ecommerce.prorider.DTOs.request.payments.CreateOrderRequest;
import com.ecommerce.prorider.DTOs.response.payments.OrderResponse;
import com.ecommerce.prorider.services.payments.OrderService;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/Order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /// Create a new order
    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    /// Get order by ID
    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable UUID orderId) {
        return orderService.findOrderById(orderId);
    }

    /// Delete order by ID
    @DeleteMapping("/{orderId}")
    public Boolean deleteOrder(@PathVariable UUID orderId) {
        return orderService.deleteOrder(orderId);
    }

    /// Retrieve all the orders base on the customerId
    @GetMapping("/{userId}")
    public List<OrderResponse> getAllOrdersByCustomer(@PathVariable UUID userId) {
        return orderService.getAllArticlesBoughtByUserId(userId);
    }

    /// Retrieve all the orders from all the stores (admin)
    @GetMapping("/Admin/All")
    public List<OrderResponse> getAllOrdersAdmin() {
        return orderService.getAllArticlesBoughtAdmin();
    }

    /// Retrieve all the orders from the database base on the userId
    @GetMapping("/findAllArticlesByUser/{userId}")
    public List<OrderResponse> getOrderByUserId(@PathVariable UUID userId) {
        return orderService.getAllArticlesBoughtByUserId(userId);
    }

    @GetMapping("/orderListExcel")
    public ByteArrayOutputStream listOrdersExcel() {
        return orderService.orderExcel();
    }
}
