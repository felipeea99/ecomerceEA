package com.ecommerce.prorider.DTOs.request.store;

import com.ecommerce.prorider.DTOs.response.store.PriceBySizeResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal price;
    private String description;

    private List<PriceBySizeResponse> pricesBySize = new ArrayList<>();
}
