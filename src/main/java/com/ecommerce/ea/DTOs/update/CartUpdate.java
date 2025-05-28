package com.ecommerce.ea.DTOs.update;

import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.Product;
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
public class CartUpdate {
    @Min(1)
    private int cartId;
    @Min(1)
    private int quantity;
    private boolean isCompleted;
    @NotNull
    private Product product;
    @NotNull
    private Customer customer;
}
