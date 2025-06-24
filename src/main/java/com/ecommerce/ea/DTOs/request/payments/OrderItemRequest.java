package com.ecommerce.ea.DTOs.request.payments;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    @NotNull
    private int productId;
    @Min(1)
    private int quantity;
}
