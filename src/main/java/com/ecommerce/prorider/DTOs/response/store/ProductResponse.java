package com.ecommerce.prorider.DTOs.response.store;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private int productId;
    private String productName;
    private boolean isActive;
    private boolean hasSizes;
    private BigDecimal price;
    private String description;
    private int quantity;
    private CategoryResponse category;
    private List<PriceBySizeResponse> pricesBySize = new ArrayList<>();
}
