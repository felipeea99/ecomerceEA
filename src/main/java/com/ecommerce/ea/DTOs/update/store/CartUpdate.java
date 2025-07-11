package com.ecommerce.ea.DTOs.update.store;

import com.ecommerce.ea.DTOs.response.store.SizeResponse;
import com.ecommerce.ea.entities.store.Size;
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
public class CartUpdate {
    @Min(1)
    private int cartId;
    @Min(1)
    private int quantity;
    @NotNull
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
