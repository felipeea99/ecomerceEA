package com.ecommerce.ea.DTOs.response.store;

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
public class ProductResponse {
    private int productId;
    private String productName;
    private boolean isActive;
    private boolean hasSizes;
    private BigDecimal price;
    private String description;

    private StoreLiteResponse store;
    private CategoryResponse category;
    private List<PriceBySizeResponse> pricesBySize = new ArrayList<>();

}
