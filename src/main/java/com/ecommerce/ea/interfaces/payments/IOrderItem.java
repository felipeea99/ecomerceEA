package com.ecommerce.ea.interfaces.payments;

import com.ecommerce.ea.DTOs.request.payments.OrderItemRequest;
import com.ecommerce.ea.DTOs.response.payments.OrderItemResponse;
import com.ecommerce.ea.entities.payments.Order;
import com.ecommerce.ea.entities.payments.OrderItem;

public interface IOrderItem {
    OrderItemResponse ToOrderItemResponse (OrderItem orderItem);
    OrderItem ToOrderItem (OrderItemRequest orderItemRequest, Order order);
}
