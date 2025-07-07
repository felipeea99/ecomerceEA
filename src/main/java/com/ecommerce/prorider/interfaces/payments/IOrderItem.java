package com.ecommerce.prorider.interfaces.payments;

import com.ecommerce.prorider.DTOs.request.payments.OrderItemRequest;
import com.ecommerce.prorider.DTOs.response.payments.OrderItemResponse;
import com.ecommerce.prorider.entities.payments.Order;
import com.ecommerce.prorider.entities.payments.OrderItem;

public interface IOrderItem {
    OrderItemResponse ToOrderItemResponse (OrderItem orderItem);
    OrderItem ToOrderItem (OrderItemRequest orderItemRequest, Order order);
}
