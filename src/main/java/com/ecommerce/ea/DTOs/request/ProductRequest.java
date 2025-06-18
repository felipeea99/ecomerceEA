package com.ecommerce.ea.DTOs.request;

import com.ecommerce.ea.entities.PriceBySize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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

    private boolean hasSizes;
    private double price;
    private List<PriceBySize> pricesBySize = new ArrayList<>();
}
