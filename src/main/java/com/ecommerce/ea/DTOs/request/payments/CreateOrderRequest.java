package com.ecommerce.ea.DTOs.request.payments;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private UUID storeId;
    private UUID customerId;
    private int addressId;
    private List<OrderItemRequest> items;
}

