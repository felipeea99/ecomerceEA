package com.ecommerce.ea.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "productName is mandatory")
    private String productName;

    private boolean isActive;

    @NotNull(message = "storeId is mandatory")
    private UUID storeId;

    @NotNull(message = "categoryId is mandatory")
    private int categoryId;
}
