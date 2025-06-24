package com.ecommerce.ea.controllers.payments;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.DTOs.request.payments.CreateOrderRequest;
import com.ecommerce.ea.DTOs.response.payments.OrderResponse;
import com.ecommerce.ea.interfaces.payments.IOrder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/Order")
public class OrderController {
    private final IOrder orderService;

    public OrderController(IOrder orderService) {
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
    @GetMapping("/Customer/{customerId}")
    public List<OrderResponse> getAllOrdersByCustomer(@PathVariable UUID customerId) {
        return orderService.getAllArticlesBoughtByCustomerId(customerId);
    }

    /// Retrieve all the orders from all the stores (admin)
    @GetMapping("/Admin/All")
    public List<OrderResponse> getAllOrdersAdmin() {
        return orderService.getAllArticlesBoughtAdmin();
    }

    /// Retrieve all the orders from a specific store
    @GetMapping("/Store/{storeId}")
    public List<OrderResponse> getAllOrdersByStore(@PathVariable UUID storeId) {
        return orderService.getAllArticlesBoughtByStoreId(storeId);
    }
    @ValidateStoreAccess
    @GetMapping("/{storeName}/orderListExcel")
    public ByteArrayOutputStream listOrdersExcel() {
        UUID storeId = StoreContextHolder.getStoreId();
        return orderService.orderExcel(storeId);
    }
}
