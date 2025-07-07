package com.ecommerce.prorider.DTOs.request.store;

import com.ecommerce.prorider.DTOs.response.store.SizeResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    @Min(1)
    private int quantity;
    private boolean isCompleted;
    @NotNull
    private int productId;
    @NotNull
    /// Might be true or false
    private boolean isSize;
    /// Might be Null
    private SizeResponse sizeObj;
    @NotNull
    private UUID customerId;
}
