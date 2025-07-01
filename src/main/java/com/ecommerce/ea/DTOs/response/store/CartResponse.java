package com.ecommerce.ea.DTOs.response.store;

import com.ecommerce.ea.entities.store.Product;
import com.ecommerce.ea.entities.store.Size;
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
public class CartResponse {
    private int cartId;
    private ProductResponse product;
    private int quantity;
    private boolean isCompleted;
    /// Might be true or false
    private boolean isSize;
    /// Might be Null
    private SizeResponse sizeObj;
    private UUID customerId;
}
