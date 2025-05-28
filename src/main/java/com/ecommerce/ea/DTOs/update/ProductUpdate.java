package com.ecommerce.ea.DTOs.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdate {
    @NotNull(message = "productId is mandatory")
    private int productId;

    @NotBlank(message = "productName is mandatory")
    private String productName;

    private boolean isActive;

    @NotNull(message = "storeId is mandatory")
    private int storeId;

    @NotNull(message = "categoryId is mandatory")
    private int categoryId;
}
