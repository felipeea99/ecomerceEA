package com.ecommerce.prorider.interfaces.payments;

import com.ecommerce.prorider.DTOs.request.payments.CreateOrderRequest;
import com.ecommerce.prorider.DTOs.response.payments.OrderResponse;
import com.ecommerce.prorider.DTOs.update.payments.OrderUpdate;
import com.ecommerce.prorider.entities.payments.Order;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public interface IOrder {
    OrderResponse createOrder(CreateOrderRequest request);
    Order findOrderByIdBaseForm(UUID orderId);
    OrderResponse findOrderById(UUID orderId);
    Order findOrderByStripePaymentId(String stripePaymentId);
    OrderResponse editOrder(OrderUpdate orderUpdate);
    Boolean deleteOrder(UUID orderId);

    List<OrderResponse> getAllArticlesBoughtByUserId(UUID userId);
    List<OrderResponse> getAllArticlesBoughtAdmin();
    ByteArrayOutputStream orderExcel();

    OrderResponse ToOrderResponse(Order order);
    Order ToOrderObject(CreateOrderRequest createOrderRequest);

}
